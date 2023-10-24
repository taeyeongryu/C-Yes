package com.cyes.webserver.domain.quiz.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Builder
public class QuizInfoResponse {

    private String quizTitle;
    private LocalDateTime quizStartDate;

    @Builder
    public QuizInfoResponse(String quizTitle, LocalDateTime quizStartDate) {
        this.quizTitle = quizTitle;
        this.quizStartDate = quizStartDate;
    }


}
