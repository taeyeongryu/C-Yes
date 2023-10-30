package com.cyes.webserver.domain.problem.dto;

import com.cyes.webserver.domain.problem.entity.Problem;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemUpdateServiceRequest {
    private String id;
    private String content;
    private String answer;
    private ProblemCategory problemCategory;
    private ProblemType problemType;

    @Builder
    public ProblemUpdateServiceRequest(String id, String content, String answer, ProblemCategory problemCategory, ProblemType problemType) {
        this.id = id;
        this.content = content;
        this.answer = answer;
        this.problemCategory = problemCategory;
        this.problemType = problemType;
    }
}
