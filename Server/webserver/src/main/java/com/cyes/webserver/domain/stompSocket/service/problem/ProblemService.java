package com.cyes.webserver.domain.stompSocket.service.problem;

import com.cyes.webserver.domain.problem.dto.ProblemResponse;
import com.cyes.webserver.domain.stompSocket.dto.ProblemMessage;
import com.cyes.webserver.domain.stompSocket.dto.SessionMessage;
import com.cyes.webserver.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service("ProblemService")
@Slf4j
@RequiredArgsConstructor
public class ProblemService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic channelTopic;
    private final RedisService redisService;

    /**
     * client에게 풀어야 할 문제를 전송하는 메서드
     * @param quizId (퀴즈id)
     * @param problem (출제할 문제 정보)
     */
    public void sendProblem(Long quizId, ProblemResponse problem) {
        ProblemMessage problemMessage = ProblemMessage.builder()
                .quizId(quizId)
                .type(SessionMessage.MessageType.PROBLEM)
                .order(problem.getProblemOrder())
                .answerLength(problem.getContentResponse().getAnswer().length())
                .question(problem.getContentResponse().getQuestion())
                .build();

        // 클라이언트한테 문제 보내기
        System.out.println("문제 전달");
        redisTemplate.convertAndSend(channelTopic.getTopic(), problemMessage);

        /* Redis에 특정 문제를 보낸 시간을 저장한다. */

        // key : quiz_id_problemOrder ( 퀴즈번호_문제순서 )
        // value : LocalDateTime.now() ( 문제를 보낸 시간 )
        String key = quizId + "_" + problem.getProblemOrder();

        // Redis에 저장
        redisService.setStringDateRedis(key, LocalDateTime.now().toString(), Duration.ofMinutes(30));

    }

}
