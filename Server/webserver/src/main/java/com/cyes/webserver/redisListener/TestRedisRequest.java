package com.cyes.webserver.redisListener;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TestRedisRequest {

    String key;
    String value;
    Integer expireTime;

}
