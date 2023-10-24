package com.cyes.webserver.domain.stompSocket.service;

import com.cyes.webserver.domain.stompSocket.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;

    @Transactional
    public void sendMessage(ChatMessage message) {

        String topic = channelTopic.getTopic();

        if (message.getType() == ChatMessage.MessageType.TALK) {
            redisTemplate.convertAndSend(topic, message);
        }
    }
}