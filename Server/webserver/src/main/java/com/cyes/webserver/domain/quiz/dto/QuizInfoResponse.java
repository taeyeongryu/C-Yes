package com.cyes.webserver.domain.quiz.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
@Builder
public class QuizInfoResponse {

    private String quizTitle;
    private String quizLink;

    @Builder
    public QuizInfoResponse(String quizTitle, String quizLink) {
        this.quizTitle = quizTitle;
        this.quizLink = quizLink;
    }

}
