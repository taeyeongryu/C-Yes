package com.cyes.webserver.redisListener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final ChannelTopic channelTopic;

    public void save(String key, Object object, Integer expireTime){

        ValueOperations<String, Object> op = redisTemplate.opsForValue();

        op.set(key, object);
        redisTemplate.expire(key, Duration.ofSeconds(expireTime));


        System.out.println(getObject(key, String.class));
    }

    public <T> T getObject(String key, Class<T> classType){

        ValueOperations<String, Object> op = redisTemplate.opsForValue();

        Object object = op.get(key);

        return objectMapper.convertValue(object, classType);
    }

    public void saveList(String key, List<Object> list) {

        ListOperations<String, Object> op = redisTemplate.opsForList();

        op.rightPushAll(key, list);
    }

    public <T> List<T> getListObject(String key, Class<T> classType){

//        ListOperations<String, Object> op = redisTemplate.opsForList();
        ListOperations<String, Object> op = redisTemplate.opsForList();
        List<Object> list = op.range(key, 0, -1);

        List<T> convertedList = objectMapper.convertValue(list, new TypeReference<List<T>>() {});

        return convertedList;
    }

}
