package com.cyes.webserver.domain.stompSocket.service;

import com.cyes.webserver.domain.stompSocket.dto.QuestionMessage;
import com.cyes.webserver.domain.stompSocket.dto.SessionMessage;
import com.cyes.webserver.exception.CustomException;
import com.cyes.webserver.exception.CustomExceptionList;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

/**
 * onMessage 메소드는 리스너에 수신된 메시지를 각 비즈니스 로직을 거쳐
 * messagingTemplate을 이용해 WebSocket 구독자들에게 메시지를 전달하는 메소드이다.
 * <p>
 * Redis로부터 온 메시지를 역직렬화하여 SessionMessage DTO로 전환뒤
 * 필요한 정보와 함께 메시지를 전달한다.
 */

@Slf4j
@RequiredArgsConstructor
@Service
//메시지를 수신하는 곳이라고 생각할 수 있다.
//MessageListener를 구현해서 이벤트가 발생할 때 실행될 작업을 onMessage를 구현해서 할 수 있다.
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    //Redis로부터 메시지가 도착했을 때 실행되는 메서드
    @Override
    //publish된 메서드가 message를 통해서 들어온다.
    public void onMessage(Message message, byte[] pattern) {
        try {
            //message.getBody()를 하면 byte형식 배열이 반환된다.
            log.info("1");
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            log.info("2");
            SessionMessage roomMessage = objectMapper.readValue(publishMessage, SessionMessage.class);
            log.info("3");

            log.info(publishMessage);
            log.info(roomMessage.toString());

            if (roomMessage.getType().equals(SessionMessage.MessageType.QUESTION)) {
                log.info("4");
                roomMessage = objectMapper.readValue(publishMessage, QuestionMessage.class);
                log.info("5");
            }

            //stomp Message Broker로 보내는 메서드
            //즉 client에게 보내는 메서드는 이곳이다. MessageService에서는 모두 Redis에 publish하는 것
            messagingTemplate.convertAndSend("/sub/quiz/session/" + roomMessage.getSessionId(), roomMessage);

        } catch (Exception e) {
            throw new CustomException(CustomExceptionList.MESSAGE_NOT_FOUND_ERROR);
        }
    }


    /*
    스케줄러가 예약된 시점에 퀴즈 참여자들에게 publish
    */
    public void sendToUsers(SessionMessage message) {

        messagingTemplate.convertAndSend("/sub/quiz/session" + message.getSessionId(), message);

    }
}
