package com.cyes.webserver.domain.stompSocket.service.result;

import com.cyes.webserver.domain.member.entity.Member;
import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.domain.problem.dto.ProblemResponse;
import com.cyes.webserver.domain.rank.dto.GradingResult;
import com.cyes.webserver.domain.stompSocket.dto.GradingResultPresentResponse;
import com.cyes.webserver.domain.stompSocket.dto.ResultMessage;
import com.cyes.webserver.domain.stompSocket.dto.SessionMessage;
import com.cyes.webserver.domain.stompSocket.dto.SubmitRedis;
import com.cyes.webserver.redis.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResultService {

    private final org.springframework.data.redis.core.StringRedisTemplate StringRedisTemplate;
    private final MemberRepository memberRepository;
    private final ChannelTopic channelTopic;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisService redisService;

    /**
     * client에게 최종 순위를 보내주는 메서드
     * @param quizId (퀴즈id)
     * @param problemResponseList
     * @throws JsonProcessingException
     */
    public void sendResult(Long quizId, List<ProblemResponse> problemResponseList) throws JsonProcessingException {

        // 해당 퀴즈로 제출된 답안 List를 Redis에서 가져온다.
        List<SubmitRedis> list = getSubmitList(quizId.toString());

        System.out.println("list = " + list);

        //채점을 완료해서 순서를 매긴 값의 리스트 이다.
        List<GradingResult> gradingResultList = getGradingResultList(list, problemResponseList);

        System.out.println("gradingResultList = " + gradingResultList);

        //채점해서 졍렬한 결과를 상위 n명만 골라서 nickname, 맞은 갯수 넘겨준다.
        List<GradingResultPresentResponse> resultPresentResponseList = gradingPresent(gradingResultList, 3);

        System.out.println("resultPresentResponseList = " + resultPresentResponseList);

        //채점 결과를 담고있는 메시지양
        ResultMessage resultMessage = ResultMessage.builder()
                .quizId(quizId)
                .gradingResultPresentResponseList(resultPresentResponseList)
                .type(SessionMessage.MessageType.RESULT)
                .build();

        System.out.println("resultMessage = " + resultMessage);

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
                String valueStr = redisService.getDataFromRedis(keyStr);

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


}
