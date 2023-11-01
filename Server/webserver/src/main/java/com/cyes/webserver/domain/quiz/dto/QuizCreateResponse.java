package com.cyes.webserver.domain.quiz.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class QuizCreateResponse {

    private Long quizId;
    private String quizTitle;
    private LocalDateTime quizStartDate;

    @Builder
    public QuizCreateResponse(Long quizId, String title, LocalDateTime quizStartDate) {
        this.quizId = quizId;
        this.quizTitle = title;
        this.quizStartDate = quizStartDate;
    }
}
