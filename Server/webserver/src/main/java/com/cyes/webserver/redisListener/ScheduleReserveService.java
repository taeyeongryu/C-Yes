package com.cyes.webserver.redisListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleReserveService {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;


    public void save(Long quizId, LocalDateTime expireTime) throws JsonProcessingException {

        ValueOperations<String, String> op = stringRedisTemplate.opsForValue();

        String quizIdStr = objectMapper.writeValueAsString(quizId);
        op.set(quizIdStr, "");

        Duration expireDuration = Duration.between(LocalDateTime.now(), expireTime);

        stringRedisTemplate.expire(quizIdStr, expireDuration);
    }

}
