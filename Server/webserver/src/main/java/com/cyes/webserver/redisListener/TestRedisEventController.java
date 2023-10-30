package com.cyes.webserver.redisListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("test/redis")
public class TestRedisEventController {

    private final TestRedisService testRedisService;

    @PostMapping
    public void testRedisExpire(@RequestBody TestRedisRequest req){
        System.out.println(req);
        testRedisService.save(req.key, req.value, req.expireTime);
    }

}
