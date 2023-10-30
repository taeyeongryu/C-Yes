package com.cyes.webserver.redisListener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * Redis의 Key가 만료될 때, 발생하는 이벤트를 핸들링하는 컴포넌튼
 */

@Slf4j
@Component
public class ExpirationListener extends KeyExpirationEventMessageListener {

    /**
     * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public ExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        
        // Session 진행 테스크 실행
        System.out.println("##### ##### onMessage pattern " + new String(pattern) + " | " + message.toString());
    }
}