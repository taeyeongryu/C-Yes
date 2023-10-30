package com.cyes.webserver.domain.problem.dto;

import com.cyes.webserver.domain.problem.dto.problemcontent.request.TrueOrFalseProblemRequest;
import com.cyes.webserver.domain.problem.entity.Problem;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrueOrFalseProblemSaveRequest {
    private TrueOrFalseProblemRequest trueOrFalseProblemRequest;
    private ProblemCategory problemCategory;


    @Builder
    public TrueOrFalseProblemSaveRequest(TrueOrFalseProblemRequest trueOrFalseProblemRequest, ProblemCategory problemCategory) {
        this.trueOrFalseProblemRequest = trueOrFalseProblemRequest;
        this.problemCategory = problemCategory;

    }

    public Problem toEntity(){
        return Problem.builder()
                .content(this.trueOrFalseProblemRequest.toEntity())
                .category(this.problemCategory)
                .type(ProblemType.TRUEORFALSE)
                .build();
    }
}
