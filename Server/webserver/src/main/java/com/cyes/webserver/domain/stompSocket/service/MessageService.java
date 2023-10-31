package com.cyes.webserver.domain.stompSocket.service;

import com.cyes.webserver.domain.Answer.entity.Answer;
import com.cyes.webserver.domain.problem.dto.ProblemResponse;
import com.cyes.webserver.domain.problem.entity.Problem;
import com.cyes.webserver.domain.problem.service.ProblemService;
import com.cyes.webserver.domain.quiz.service.QuizService;
import com.cyes.webserver.domain.quizproblem.repository.QuizProblemRepository;
import com.cyes.webserver.domain.rank.dto.GradingResult;
import com.cyes.webserver.domain.stompSocket.dto.*;
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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final ChannelTopic channelTopic;
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate StringRedisTemplate;
    private final QuizProblemRepository quizProblemRepository;
    private final ProblemService problemService;
    private final QuizService quizService;
    private final ObjectMapper objectMapper;

    //
    public List<ProblemResponse> startSession(Long quizId) {
        List<String> list = quizProblemRepository.findQuizProblems(quizId);
        // (문제, 정답) 리스트 조회
        List<ProblemResponse> problemAnswerList = problemService.findAllProblemByQuiz(list);
        // 클라이언트한테 시작 신호 보내기
        redisTemplate.convertAndSend(channelTopic.getTopic(), new SessionMessage(quizId, SessionMessage.MessageType.START));

        return problemAnswerList;
    }


    //client에게 풀어야 할 문제를 전송하는 메서드
    public void sendQuestion(Long quizId, ProblemResponse problem) {
        QuestionMessage questionMessage = QuestionMessage.builder()
                .sessionId(quizId)
                .type(SessionMessage.MessageType.PROBLEM)
                .question(problem.getContentResponse().getQuestion())
                .build();
        //문제를 전송한 시간
        LocalDateTime sendTime = LocalDateTime.now();
        //Redis에 특정 문제를 보낸 시간을 저장한다.


        // 클라이언트한테 문제 보내기
        redisTemplate.convertAndSend(channelTopic.getTopic(), questionMessage);

    }

    //client는 다음 문제로 넘어가기 전에 정답을 확인할 수 있다.
    //client한테 문제의 정답을 보여주는 메서드
    public void sendAnswer(Long quizId, ProblemResponse problem) {
        AnswerMessage answerMessage = AnswerMessage.builder()
                .sessionId(quizId)
                .type(SessionMessage.MessageType.ANSWER)
                .answer(problem.getContentResponse().getAnswer())
                .build();

        // 클라이언트한테 답 보내기
        redisTemplate.convertAndSend(channelTopic.getTopic(), answerMessage);
    }

    //client에게 퀴즈가 종료되었음을 알리는 메서드
    public void sendEnd(Long quizId){

        SessionMessage endMessage = new SessionMessage(quizId, SessionMessage.MessageType.END);

        redisTemplate.convertAndSend(channelTopic.getTopic(), endMessage);
    }

    //client에게 최종 순위를 보내주는 메서드
    public void sendResult(Long quizId) {

        SessionMessage resultMessage = new SessionMessage(quizId, SessionMessage.MessageType.RESULT);

        redisTemplate.convertAndSend(channelTopic.getTopic(), resultMessage);
    }


    public void handleEnter(SessionMessage message) {

    }

    public void handleSubmit(SubmitMessage message){

    }

    public void handleChat(ChatMessage message){

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

    public List<GradingResult> getGradingResultList(List<Answer> answerList, List<ProblemResponse> problemList) {
        //key : memgerId, value : 채점 결과
        Map<Long, GradingResult> resultMap = new HashMap<>();

        for (int i = 0; i < answerList.size(); i++) {
            Answer answer = answerList.get(i);
            //제출 답안
            String submit = answer.getSubmitContent();

            //memberId
            Long memberId = answer.getMemberId();


            //문제 번호
            Integer problemNumber = answer.getProblemNumber();

            //문제의 정답
            String problemAnswer = problemList.get(problemNumber - 1).getContentResponse().getAnswer();

            //제출 답안이랑 정답이랑 같으면
            if(submit.equals(problemAnswer)){
                GradingResult result = resultMap.getOrDefault(memberId, GradingResult.builder().memberId(memberId).build());

                result.addCorrectCount();
                result.addDuringTime(answer.getDuringTime());

                resultMap.put(memberId, result);
            }

        }
        List<GradingResult> resultList = new ArrayList(resultMap.values());
        Collections.sort(resultList);
        System.out.println("resultList = " + resultList);

        return resultList;
    }

}
