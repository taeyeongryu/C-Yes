package com.cyes.webserver.domain.Answer.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerResponse {
    private String id;
    private Long memberId;
    private Long quizId;
    private Integer problemNumber;
    private String submitContent;

    @Builder
    public AnswerResponse(String id, Long memberId, Long quizId, Integer problemNumber, String submitContent) {
        this.id = id;
        this.memberId = memberId;
        this.quizId = quizId;
        this.problemNumber = problemNumber;
        this.submitContent = submitContent;
    }
}
