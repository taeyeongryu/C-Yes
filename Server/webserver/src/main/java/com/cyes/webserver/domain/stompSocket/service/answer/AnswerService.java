package com.cyes.webserver.domain.stompSocket.service.answer;

import com.cyes.webserver.domain.problem.dto.ProblemResponse;
import com.cyes.webserver.domain.stompSocket.dto.AnswerMessage;
import com.cyes.webserver.domain.stompSocket.dto.SessionMessage;
import com.cyes.webserver.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service("AnswerService")
@Slf4j
@RequiredArgsConstructor
public class AnswerService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic channelTopic;

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

}
