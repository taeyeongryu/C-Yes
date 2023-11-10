package com.cyes.webserver.domain.quiz.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupQuizInfoResponse {

    private Long quizId;
    private String quizTitle;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime quizStartDate;

    @Builder
    public GroupQuizInfoResponse(Long quizId, String quizTitle, LocalDateTime quizStartDate) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.quizStartDate = quizStartDate;
    }
}
