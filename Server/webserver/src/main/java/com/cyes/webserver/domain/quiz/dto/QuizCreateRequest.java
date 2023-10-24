package com.cyes.webserver.domain.quiz.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
public class QuizCreateRequest {

    private String quizTitle;
    private LocalDateTime quizStartDate;
    private List<String> problemList;

    public QuizCreateRequestToService create() {

        QuizCreateRequestToService quizCreateRequestToService = QuizCreateRequestToService.builder()
                .quizTitle(this.quizTitle)
                .quizStartDate(this.quizStartDate)
                .problemList(this.problemList)
                .build();

        return quizCreateRequestToService;

    }


}
