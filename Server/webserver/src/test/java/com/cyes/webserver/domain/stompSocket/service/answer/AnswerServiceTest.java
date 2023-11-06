package com.cyes.webserver.domain.stompSocket.service.answer;

import com.cyes.webserver.domain.problem.dto.ProblemResponse;
import com.cyes.webserver.domain.problem.dto.problemcontent.response.ProblemContentResponse;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import com.cyes.webserver.domain.stompSocket.dto.AnswerMessage;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AnswerServiceTest {
    @Autowired
    private AnswerService answerService;


//    @DisplayName("channelTopic으로 문제의 정답인 AnswerMessage를 publish한다.")
//    @Test
//    void sendAnswer(){
//        //given
//        Long quizId = 1L;
//        ProblemResponse problemResponse = ProblemResponse.builder().id("problemId").contentResponse(ProblemContentResponse.builder().answer("답").build()).problemOrder(1).category("ALGORITHM").type("MULTIPLECHOICE").build();
//
//        //when
//
//        answerService.sendAnswer(quizId,problemResponse);
//
//        //then
//        verify(redisTemplate,times(1)).convertAndSend(anyString(),any(AnswerMessage.class));
//    }
}