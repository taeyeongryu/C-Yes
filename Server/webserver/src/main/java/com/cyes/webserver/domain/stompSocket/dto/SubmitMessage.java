package com.cyes.webserver.domain.stompSocket.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Slf4j
public class SubmitMessage extends SessionMessage{
    Long memberId;
    Long quizId;
    Integer problemOrder;
    String submitContent;

    public String createKey() {
        return this.quizId + "_" + this.problemOrder + "_" + this.memberId;
    }

    public SubmitRedis ToSubmitRedis(LocalDateTime startTime) {

        Duration duringTime = Duration.between(startTime,LocalDateTime.now());

        return SubmitRedis.builder()
                .quizId(this.quizId)
                .problemOrder(this.problemOrder)
                .memberId(this.memberId)
                .submitContent(this.submitContent)
                .duringTime(duringTime.getSeconds())
                .build();
    }

}
