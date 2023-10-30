package com.cyes.webserver.redisListener;

import com.cyes.webserver.exception.CustomException;
import com.cyes.webserver.exception.CustomExceptionList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * Redis의 Key가 만료될 때, 발생하는 이벤트를 핸들링하는 컴포넌트
 */

@Slf4j
@Component
public class ExpirationListener extends KeyExpirationEventMessageListener {

    private final ScheduledTaskFactory scheduledTaskFactory;

    /**
     * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
     *
     * @param listenerContainer must not be {@literal null}.
     */
    public ExpirationListener(RedisMessageListenerContainer listenerContainer,
                              ScheduledTaskFactory scheduledTaskFactory
    ) {
        super(listenerContainer);
        this.scheduledTaskFactory = scheduledTaskFactory;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 예약된 메소드 실행
        try {
            scheduledTaskFactory.sessionTask(message.toString());
        } catch (InterruptedException e) {
            throw new CustomException(CustomExceptionList.SCHEDULE_EXE_FAIL_ERROR);
        }
    }
}