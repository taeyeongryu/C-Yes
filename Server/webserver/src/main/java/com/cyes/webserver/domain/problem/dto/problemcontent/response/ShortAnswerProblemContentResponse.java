package com.cyes.webserver.domain.problem.dto.problemcontent.response;

import lombok.Getter;

@Getter
public class ShortAnswerProblemContentResponse extends ProblemContentResponse{
    public ShortAnswerProblemContentResponse(String question, String answer) {
        super(question, answer);
    }
}
