package com.cyes.webserver.domain.stompSocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

/**
 * onMessage 메소드는 리스너에 수신된 메시지를 각 비즈니스 로직을 거쳐
 * messagingTemplate을 이용해 WebSocket 구독자들에게 메시지를 전달하는 메소드이다.
 *
 * Redis로부터 온 메시지를 역직렬화하여 ChatMessage DTO로 전환뒤
 * 필요한 정보와 함께 메시지를 전달한다.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {

    }
}
