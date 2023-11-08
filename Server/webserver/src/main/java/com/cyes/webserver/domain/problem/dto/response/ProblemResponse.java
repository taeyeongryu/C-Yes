package com.cyes.webserver.domain.problem.dto.response;


import lombok.*;

import java.io.IOException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemResponse {
    private String id;
    private String question;
    private String[] choices;
    private int problemOrder;
    private String answer;
    private String description;
    private String category;
    private String type;

    @Builder
    public ProblemResponse(String id, String question, String[] choices, int problemOrder, String answer, String description, String category, String type) {
        this.id = id;
        this.question = question;
        this.choices = choices;
        this.problemOrder = problemOrder;
        this.answer = answer;
        this.description = description;
        this.category = category;
        this.type = type;
    }
}
