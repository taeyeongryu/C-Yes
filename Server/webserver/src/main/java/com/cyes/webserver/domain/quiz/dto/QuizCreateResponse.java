package com.cyes.webserver.domain.quiz.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizCreateResponse {

    private Long quizId;
    private String quizTitle;

    @Builder
    public QuizCreateResponse(Long quizId, String title) {
        this.quizId = quizId;
        this.quizTitle = title;
    }
}
