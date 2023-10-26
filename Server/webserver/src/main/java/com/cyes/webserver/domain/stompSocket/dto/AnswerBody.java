package com.cyes.webserver.domain.stompSocket.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class AnswerBody {
    String answer;

    @Builder
    public AnswerBody(String answer) {
        this.answer = answer;
    }
}
