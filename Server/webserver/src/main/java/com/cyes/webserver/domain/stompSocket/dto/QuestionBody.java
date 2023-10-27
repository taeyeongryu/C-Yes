package com.cyes.webserver.domain.stompSocket.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class QuestionBody {
    String question;
    List<String> selections;

    public enum QuestionType {
        SINGLE, FOUR, OX
    }

    @Builder
    public QuestionBody(String question, List<String> selections) {
        this.question = question;
        this.selections = selections;
    }
}
