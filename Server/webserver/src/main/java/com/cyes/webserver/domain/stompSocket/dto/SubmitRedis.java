package com.cyes.webserver.domain.stompSocket.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.GeneratedValue;
import java.time.Duration;
import java.time.LocalDateTime;


@RedisHash("Submit")
@Data
@Getter
@ToString
public class SubmitRedis {


    Long quizId;
    Long memberId;
    Integer problemOrder;
    String submitContent;
    Long duringTime;

    public SubmitRedis() {

    }

    @Builder
    public SubmitRedis(Long quizId, Long memberId, Integer problemOrder, String submitContent, Long duringTime) {
        this.quizId = quizId;
        this.memberId = memberId;
        this.problemOrder = problemOrder;
        this.submitContent = submitContent;
        this.duringTime = duringTime;
    }



}
