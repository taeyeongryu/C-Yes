package com.cyes.webserver.domain.stompSocket.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class AnswerMessage extends SessionMessage{
    String answer;

    @Builder
    public AnswerMessage(String answer) {
        this.answer = answer;
    }
}
