package com.cyes.webserver.domain.stompSocket.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.Duration;
import java.time.LocalDateTime;


@RedisHash("Submit")
@Data
@Getter
@RequiredArgsConstructor
public class SubmitRedis {

    @Id
    Long quizId;
    Long memberId;
    Integer problemOrder;
    String submitContent;
    Long duringTime;
    @Builder
    public SubmitRedis(Long memberId, Long quizId, Integer problemOrder, String submitContent, Long duringTime) {
        this.memberId = memberId;
        this.quizId = quizId;
        this.problemOrder = problemOrder;
        this.submitContent = submitContent;
        this.duringTime = duringTime;
    }

}
