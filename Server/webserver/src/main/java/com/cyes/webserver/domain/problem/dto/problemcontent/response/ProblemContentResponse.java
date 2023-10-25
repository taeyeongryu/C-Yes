package com.cyes.webserver.domain.problem.dto.problemcontent.response;


import lombok.Builder;
import lombok.Getter;

@Getter
public class ProblemContentResponse {
    //문제
    String question;
    //답
    String answer;

    @Builder
    public ProblemContentResponse(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

}
