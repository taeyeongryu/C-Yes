package com.cyes.webserver.domain.stompSocket.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AnswerMessage extends SessionMessage{
    String answer;

    @Builder
    public AnswerMessage(String sessionId, MessageType type, String answer) {
        super(sessionId, type);
        this.answer = answer;
    }

}
