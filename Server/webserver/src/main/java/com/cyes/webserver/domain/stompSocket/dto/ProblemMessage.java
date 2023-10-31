package com.cyes.webserver.domain.stompSocket.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProblemMessage extends SessionMessage{
    String question;
    Integer order;
    List<String> selections;

    public enum QuestionType {
        SINGLE, FOUR, OX
    }


    @Builder
    public ProblemMessage(Long sessionId, MessageType type, String question, List<String> selections) {
        super(sessionId, type);
        this.question = question;
        this.selections = selections;
    }

}
