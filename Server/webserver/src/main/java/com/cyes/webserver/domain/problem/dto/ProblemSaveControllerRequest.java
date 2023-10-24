package com.cyes.webserver.domain.problem.dto;

import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemSaveControllerRequest {
    private String content;
    private String answer;
    private ProblemCategory problemCategory;
    private ProblemType problemType;

    @Builder
    public ProblemSaveControllerRequest(String content, String answer, ProblemCategory problemCategory, ProblemType problemType) {
        this.content = content;
        this.answer = answer;
        this.problemCategory = problemCategory;
        this.problemType = problemType;
    }

    public ProblemSaveServiceRequest toServiceRequest(){
        return ProblemSaveServiceRequest.builder()
                .content(this.content)
                .answer(this.answer)
                .problemCategory(this.problemCategory)
                .problemType(this.problemType)
                .build();
    }
}
