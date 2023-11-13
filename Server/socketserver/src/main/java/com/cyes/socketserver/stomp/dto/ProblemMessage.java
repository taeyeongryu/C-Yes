package com.cyes.socketserver.stomp.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProblemMessage extends SessionMessage{
    String question;
    Integer order;
    Integer answerLength;
    List<String> selections;

    public enum QuestionType {
        SINGLE, FOUR, OX
    }


    @Builder
    public ProblemMessage(Long quizId, MessageType type, String question, Integer order, Integer answerLength, List<String> selections) {
        super(quizId, type);
        this.question = question;
        this.order = order;
        this.answerLength = answerLength;
        this.selections = selections;
    }

}
