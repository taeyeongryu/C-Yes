package com.cyes.webserver.domain.stompSocket.service;

import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.domain.problem.dto.ProblemResponse;
import com.cyes.webserver.domain.problem.service.ProblemService;
import com.cyes.webserver.domain.quizproblem.repository.QuizProblemRepository;
import com.cyes.webserver.domain.rank.dto.GradingResult;
import com.cyes.webserver.domain.stompSocket.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;


    /**
     * client에게 퀴즈쇼 시작 신호를 전송하는 메서드
     * @param quizId (퀴즈id)
     * @return List<problemResponse> 퀴즈 정보
     */
    public List<ProblemResponse> startSession(Long quizId) {

        // 퀴즈 문제 pk 조회
        List<String> list = quizProblemRepository.findQuizProblems(quizId);
        // (문제, 정답) 리스트 조회
        List<ProblemResponse> problemAnswerList = problemService.findAllProblemByQuiz(list);
        // 클라이언트한테 시작 신호 보내기
        redisTemplate.convertAndSend(channelTopic.getTopic(), new SessionMessage(quizId, SessionMessage.MessageType.START));

        return problemAnswerList;
    }

    /**
     * client에게 풀어야 할 문제를 전송하는 메서드
     * @param quizId (퀴즈id)
     * @param problem (출제할 문제 정보)
     */
    public void sendProblem(Long quizId, ProblemResponse problem) {
        ProblemMessage problemMessage = ProblemMessage.builder()
                .sessionId(quizId)
                .type(SessionMessage.MessageType.PROBLEM)
                .question(problem.getContentResponse().getQuestion())
                .build();

        // 클라이언트한테 문제 보내기
        redisTemplate.convertAndSend(channelTopic.getTopic(), problemMessage);

        /* Redis에 특정 문제를 보낸 시간을 저장한다. */

        // key : quiz_id_problemOrder ( 퀴즈번호_문제순서 )
        // value : LocalDateTime.now() ( 문제를 보낸 시간 )
        String key = quizId + "_" + problem.getProblemOrder();
        
        // Redis에 저장
        setStringDateRedis(key, LocalDateTime.now().toString(), Duration.ofMinutes(30));

    }

    /**
     * client한테 문제의 정답을 보여주는 메서드
     * @param quizId (퀴즈id)
     * @param problem (퀴즈정보)
     */
    public void sendAnswer(Long quizId, ProblemResponse problem) {
        AnswerMessage answerMessage = AnswerMessage.builder()
                .sessionId(quizId)
                .type(SessionMessage.MessageType.ANSWER)
                .answer(problem.getContentResponse().getAnswer())
                .build();

        // 클라이언트한테 답 보내기
        redisTemplate.convertAndSend(channelTopic.getTopic(), answerMessage);
    }


    /**
     * client에게 퀴즈가 종료되었음을 알리는 메서드
     * @param quizId (퀴즈id)
     */
    public void sendEnd(Long quizId) {

        SessionMessage endMessage = new SessionMessage(quizId, SessionMessage.MessageType.END);

        redisTemplate.convertAndSend(channelTopic.getTopic(), endMessage);
    }

    /**
     * client에게 최종 순위를 보내주는 메서드
     * @param quizId (퀴즈id)
     * @param problemResponseList
     * @throws JsonProcessingException
     */
    public void sendResult(Long quizId, List<ProblemResponse> problemResponseList) throws JsonProcessingException {

        // 해당 퀴즈로 제출된 답안 List를 Redis에서 가져온다.
        List<SubmitRedis> list = getSubmitList(quizId.toString());

        //채점을 완료해서 순서를 매긴 값의 리스트 이다.
        List<GradingResult> gradingResultList = getGradingResultList(list, problemResponseList);

        //채점해서 졍렬한 결과를 상위 n명만 골라서 nickname, 맞은 갯수 넘겨준다.
        List<GradingResultPresentResponse> resultPresentResponseList = gradingPresent(gradingResultList, 3);

        //채점 결과를 담고있는 메시지양
        SessionMessage resultMessage = ResultMessage.builder()
                .quizId(quizId)
                .gradingResultPresentResponseList(resultPresentResponseList)
                .type(SessionMessage.MessageType.RESULT)
                .build();

        //Redis에 publish
        redisTemplate.convertAndSend(channelTopic.getTopic(), resultMessage);
    }

    /**
     * 해당 퀴즈로 제출된 답안 List 조회하는 메서드
     * @param quizId (퀴즈 정보)
     * @return List<SubmitRedis> Redis에 퀴즈id로 저장된 제출 정보 List
     */
    public List<SubmitRedis> getSubmitList(String quizId) {

        // 클라이언트가 제출한 정보를 keypattern(submit_퀴즈id*)으로 검색
        String keyPattern = "submit_" + quizId + "*";

        // keypattern으로 검색이 된 key들을 select
        Set<String> values = StringRedisTemplate.keys(keyPattern);

        // Redis에서 가져온 제출 정보를 담을 List 선언
        List<SubmitRedis> submitRedisList = new ArrayList<>();

        // 역직렬화를 위한 objectMapper 선언.
        ObjectMapper objectMapper = new ObjectMapper();
        for (String keyStr : values) {
            try {
                // 제출 정보 key값으로 검색하여 직렬화하여 스트링으로 저장된 제출 정보를 가져온다.
                String valueStr = getDataFromRedis(keyStr);

                // 가져온 스트링 제출 정보를 SubmitRedis 객체 형태로 역직렬화한다.
                SubmitRedis submitRedis = objectMapper.readValue(valueStr, SubmitRedis.class);

                // 역직렬화한 SubmitRedis 객체를 List에 추가.
                submitRedisList.add(submitRedis);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return submitRedisList;
    }

    /**
     * 클라이언트가 보낸 채팅 정보를 처리하는 메서드
     * @param message(ChatMessage)
     */
    public void handleChat(ChatMessage message) {

    }

    /**
     * 퀴즈 입장을 처리하는 메서드
     */
    public void handleEnter(SessionMessage message) {

    }

    /**
     * 클라이언트의 답안 제출을 처리하는 메서드
     * @param message(SubmitMessage)
     * @throws JsonProcessingException
     */
    public void handleSubmit(SubmitMessage message) throws JsonProcessingException {

        // key : 퀴즈번호_문제순서_멤버id
        String key = message.createKey();

        // value : SubmitDto
        SubmitRedis submitRedis = message.ToSubmitRedis(LocalDateTime.parse(getDataFromRedis(getRedisKey(message))),LocalDateTime.now());

        // redis에 답안 제출 정보 저장
        setSubmitDateRedis(key, submitRedis, Duration.ofMinutes(30));

    }

    private List<GradingResultPresentResponse> gradingPresent(List<GradingResult> gradingResultList, int num) {
        //최종 결과를 보여주는 것 갯수가 참여 멤버보다 크다면 모든 멤버 결과를 보여준다.
        if (num > gradingResultList.size()){num = gradingResultList.size();}

        //채점 결과를 원하는 만큼 자른다.
        List<GradingResult> slicedList = gradingResultList.subList(0, num);

        //member들의 id만 모아놓은 list
        List<Long> memberIdList = new ArrayList<>();
        for (GradingResult gradingResult : slicedList) {
            memberIdList.add(gradingResult.getMemberId());
        }

        //memberIdList로 그 Member들을 가져온다.
        List<Member> findAllMemberList = memberRepository.findAllById(memberIdList);

        //memberId를 key로 memberNickName을 value로 담는다.
        Map<Long, String> memberIdToNickName = new HashMap<>();
        for (Member member : findAllMemberList) {
            memberIdToNickName.put(member.getMemberId(), member.getMemberNickname());
        }

        List<GradingResultPresentResponse> resultPresentResponseList = changeGradingResultPresentResponses(slicedList, memberIdToNickName);

        return resultPresentResponseList;
    }

    public List<GradingResult> getGradingResultList(List<SubmitRedis> answerList, List<ProblemResponse> problemList) {
        //key : memgerId, value : 채점 결과
        Map<Long, GradingResult> resultMap = new HashMap<>();

        for (int i = 0; i < answerList.size(); i++) {
            SubmitRedis answer = answerList.get(i);
            //제출 답안
            String submit = answer.getSubmitContent();

            //memberId
            Long memberId = answer.getMemberId();


            //문제 번호
            Integer problemNumber = answer.getProblemOrder();

            //문제의 정답
            String problemAnswer = problemList.get(problemNumber - 1).getContentResponse().getAnswer();

            //제출 답안이랑 정답이랑 같으면
            if (submit.equals(problemAnswer)) {
                GradingResult result = resultMap.getOrDefault(memberId, GradingResult.builder().memberId(memberId).build());

                result.addCorrectCount();
                result.addDuringTime(answer.getDuringTime());

                resultMap.put(memberId, result);
            }

        }
        //전체 참여자를 순위를 매기고 정렬
        List<GradingResult> resultList = new ArrayList(resultMap.values());
        Collections.sort(resultList);


        System.out.println("resultList = " + resultList);

        return resultList;
    }

    private static List<GradingResultPresentResponse> changeGradingResultPresentResponses(List<GradingResult> slicedList, Map<Long, String> memberIdToNickName) {
        //return할 List를 선언한다.
        List<GradingResultPresentResponse> resultPresentResponseList = new ArrayList<>();

        //slicedList를 GradingResultPresentResponse로 바꿔서 반환한다.
        for (GradingResult gradingResult : slicedList) {
            GradingResultPresentResponse presentResponse = GradingResultPresentResponse
                    .builder()
                    .memberId(gradingResult.getMemberId())
                    .nickName(memberIdToNickName.get(gradingResult.getMemberId()))
                    .correctProblemCount(gradingResult.getCorrectProblemCount())
                    .build();
            resultPresentResponseList.add(presentResponse);
        }
        return resultPresentResponseList;
    }


    /**
     * key로 Redis에 저장된 값(String)을 조회하는 메서드 ( 문제 출제 시간 조회, 제출된 답안 조회 )
     * @param key
     * @return key에 대한 value
     */
    public String getDataFromRedis(String key) {
        return StringRedisTemplate.opsForValue().get(key);
    }

    /**
     * Redis 검색을 위한 key를 만드는 메서드
     * @param message(Submitmessage)
     * @return quizId_problemOrder (String)
     */
    public String getRedisKey(SubmitMessage message) {
        return message.getQuizId() + "_" + message.getProblemOrder();
    }

    /**
     * Redis에 문제 출제 시간을 저장하기 위한 메서드
     * @param key
     * @param value
     * @param duration
     */
    public void setStringDateRedis(String key, String value, Duration duration) {

        // redis에 접근할 수 있는 객체 선언
        ValueOperations<String, String> stringListValueOperations = StringRedisTemplate.opsForValue();

        // Redis에 출제 시간 데이터 저장
        stringListValueOperations.set(key, value, duration);
    }

    /**
     * 클라이언트가 보낸 답안 정보를 Redis에 기록하는 메소드
     * @param key
     * @param submitRedis (답안 제출 정보)
     * @param duration
     * @throws JsonProcessingException
     */
    public void setSubmitDateRedis(String key, SubmitRedis submitRedis, Duration duration) throws JsonProcessingException {

        ValueOperations<String, String> valueOperations = StringRedisTemplate.opsForValue();

        // SubmitRedis객체를 String으로 직렬화한다.
        String valueStr = objectMapper.writeValueAsString(submitRedis);
        // Redis에 답안 제출 정보(String) 데이터 저장
        valueOperations.set(key, valueStr, duration);

    }

}
