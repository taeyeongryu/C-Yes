package com.cyes.webserver.domain.problem.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemResponse {
    private String id;
    private String content;
    private String answer;
    private String category;
    private String type;

    @Builder
    public ProblemResponse(String id, String content, String answer, String category, String type) {
        this.id = id;
        this.content = content;
        this.answer = answer;
        this.category = category;
        this.type = type;
    }
}
