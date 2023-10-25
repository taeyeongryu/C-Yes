package com.cyes.webserver.domain.problem.dto.problemcontent.response;

import lombok.Getter;

@Getter
public class TrueOrFalseProblemContentResponse extends ProblemContentResponse{
    public TrueOrFalseProblemContentResponse(String question, String answer) {
        super(question, answer);
    }
}
