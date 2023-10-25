package com.cyes.webserver.domain.problem.dto.problemcontent.response;

import lombok.Getter;

@Getter
public class MultipleChoiceProblemContentResponse extends ProblemContentResponse{
    //보기
    String[] choices;


    public MultipleChoiceProblemContentResponse(String question, String answer, String[] choices) {
        super(question, answer);
        this.choices = choices;
    }
}
