package com.cyes.webserver.redisListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("test/redis")
public class TestRedisEventController {

    private final TestRedisService testRedisService;

    @GetMapping
    public void testRedisExpire(){

        for(int i=0; i<100; i++) {
            String quizId = "quiz" + i;
            testRedisService.save(quizId, "", 5);
        }
    }

}
