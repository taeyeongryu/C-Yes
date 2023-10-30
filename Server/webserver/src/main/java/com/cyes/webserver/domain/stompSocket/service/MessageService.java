package com.cyes.webserver.domain.stompSocket.service;

import com.cyes.webserver.domain.problem.dto.ProblemResponse;
import com.cyes.webserver.domain.problem.service.ProblemService;
import com.cyes.webserver.domain.quiz.service.QuizService;
import com.cyes.webserver.domain.quizproblem.repository.QuizProblemRepository;
import com.cyes.webserver.domain.stompSocket.dto.AnswerMessage;
import com.cyes.webserver.domain.stompSocket.dto.QuestionMessage;
import com.cyes.webserver.domain.stompSocket.dto.SessionMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final StringRedisTemplate StringRedisTemplate;
    private final QuizProblemRepository quizProblemRepository;
    private final ProblemService problemService;
    private final QuizService quizService;
    private final ObjectMapper objectMapper;


    //퀴즈가 종료될 때 0으로 초기화 해야함
    private int cnt;

    @Transactional
    public void sendMessage(SessionMessage message) {

        String topic = channelTopic.getTopic();

        if(message.getType().equals(SessionMessage.MessageType.SUBMIT)) {
            
        }

        redisTemplate.convertAndSend(topic, message);
    }

    public List<ProblemResponse> startSession(Long quizId) {
        List<String> list = quizProblemRepository.findQuizProblems(quizId);
        System.out.println("list = " + list);
        // (문제, 정답) 리스트 조회
        List<ProblemResponse> problemAnswerList = problemService.findAllProblemByQuiz(list);
        System.out.println("problemAnswerList = " + problemAnswerList);
        // 클라이언트한테 시작 신호 보내기
        redisTemplate.convertAndSend(channelTopic.getTopic(), new SessionMessage(quizId, SessionMessage.MessageType.START));

        return problemAnswerList;
    }

    public void sendQuestion(Long quizId, ProblemResponse problem) {
        QuestionMessage questionMessage = QuestionMessage.builder()
                .sessionId(quizId)
                .type(SessionMessage.MessageType.PROBLEM)
                .question(problem.getContentResponse().getQuestion())
                .build();

        // 클라이언트한테 문제 보내기
        redisTemplate.convertAndSend(channelTopic.getTopic(), questionMessage);
    }
    
    public void sendAnswer(Long quizId, ProblemResponse problem) {
        AnswerMessage answerMessage = AnswerMessage.builder()
                .sessionId(quizId)
                .type(SessionMessage.MessageType.ANSWER)
                .answer(problem.getContentResponse().getAnswer())
                .build();

        // 클라이언트한테 답 보내기
        redisTemplate.convertAndSend(channelTopic.getTopic(), answerMessage);
    }

    public void endSolve(Long quizId){

        SessionMessage endMessage = new SessionMessage(quizId, SessionMessage.MessageType.END);

        redisTemplate.convertAndSend(channelTopic.getTopic(), endMessage);
    }

    public void sendResult(Long quizId) {
        SessionMessage resultMessage = new SessionMessage(quizId, SessionMessage.MessageType.RESULT);

        redisTemplate.convertAndSend(channelTopic.getTopic(), resultMessage);
    }

    public void sendToUsers(SessionMessage message) throws JsonProcessingException {

        String topic = channelTopic.getTopic();

        switch (message.getType()) {
            case START:
                // 진행하는 퀴즈에 대한 문제pk 리스트 조회
                List<String> list = quizProblemRepository.findQuizProblems(quizService.searchQuiz().getQuizId());

                // (문제, 정답) 리스트 조회
                List<ProblemResponse> problemAnswerList = problemService.findAllProblemByQuiz(list);

                // Redis에 problemAnswerList(문제,정답) 넣어놓는다. 만료기한은 30분
                setDateExpire("ProblemAnswer", problemAnswerList, Duration.ofMinutes(30));

                // 클라이언트한테 시작 신호 보내기
                redisTemplate.convertAndSend(topic, message);
                break;

            case PROBLEM:
                // redis에서 문제 꺼내오기
                String question = getDataFromRedis("ProblemAnswer", "question", cnt);

                QuestionMessage questionMessage = QuestionMessage.builder()
                        .type(SessionMessage.MessageType.PROBLEM)
                        .question("아이우에오")
                        .build();

                // 클라이언트한테 문제 보내기
                redisTemplate.convertAndSend(topic, questionMessage);
                break;

            case ANSWER:
                // redis에서 문제 꺼내오기
                String answer = getDataFromRedis("ProblemAnswer", "answer", cnt++);

                AnswerMessage answerMessage = AnswerMessage.builder()
                        .type(SessionMessage.MessageType.ANSWER)
                        .answer(answer)
                        .build();

                // 클라이언트한테 문제 보내기
                redisTemplate.convertAndSend(topic, answerMessage);
                break;

            // 클라이언트한테 결과,종료 신호 보내기
            case END:
            case RESULT:
                redisTemplate.convertAndSend(topic, message);
                break;


        }

    }

    public String getDataFromRedis(String key, String what, int cnt) throws JsonProcessingException {

        String data = StringRedisTemplate.opsForValue().get(key);
        List<ProblemResponse> problemResponseList = objectMapper.readValue(data, new TypeReference<List<ProblemResponse>>() {
        });

        switch (what) {
            case "question":
                return problemResponseList.get(cnt).getContentResponse().getQuestion();
            case "answer":
                return problemResponseList.get(cnt).getContentResponse().getAnswer();
        }


        return "";
    }


    public void setDateExpire(String key, Object problemAnswerList, Duration duration) throws JsonProcessingException {

        // Redis에 데이터 저장 (리스트 형식으로)
        ValueOperations<String, String> stringListValueOperations = StringRedisTemplate.opsForValue();

        String jsonToStr = objectMapper.writeValueAsString(problemAnswerList);
        stringListValueOperations.set(key, jsonToStr, duration);

    }


}