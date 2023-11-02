package com.cyes.webserver.domain.stompSocket.service.submit;

import com.cyes.webserver.domain.stompSocket.dto.SubmitMessage;
import com.cyes.webserver.domain.stompSocket.dto.SubmitRedis;
import com.cyes.webserver.redis.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubmitService {

    private final RedisService redisService;

    /**
     * 클라이언트의 답안 제출을 처리하는 메서드
     * @param message(SubmitMessage)
     * @throws JsonProcessingException
     */
    public void handleSubmit(SubmitMessage message) throws JsonProcessingException {

        // key : 퀴즈번호_문제순서_멤버id
        String key = message.createKey();

        // value : SubmitDto
        SubmitRedis submitRedis = message.ToSubmitRedis(LocalDateTime.parse(redisService.getDataFromRedis(getRedisKey(message))),LocalDateTime.now());

        // redis에 답안 제출 정보 저장
        redisService.setSubmitDateRedis(key, submitRedis, Duration.ofMinutes(30));

    }


    /**
     * Redis 검색을 위한 key를 만드는 메서드
     * @param message(Submitmessage)
     * @return quizId_problemOrder (String)
     */
    public String getRedisKey(SubmitMessage message) {
        return message.getQuizId() + "_" + message.getProblemOrder();
    }



}
