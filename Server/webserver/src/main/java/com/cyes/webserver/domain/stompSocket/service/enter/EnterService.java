package com.cyes.webserver.domain.stompSocket.service.enter;

import com.cyes.webserver.domain.stompSocket.dto.SessionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnterService {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 퀴즈 입장을 처리하는 메서드
     */
    public void handleEnter(SessionMessage message) {
        String key = "total_participant_"+message.getQuizId();

        stringRedisTemplate.opsForValue().increment(key);
    }
}
