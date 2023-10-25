package com.cyes.webserver.domain.problem.dto;

import com.cyes.webserver.domain.problem.dto.problemcontent.request.MultipleChoiceProblemRequest;
import com.cyes.webserver.domain.problem.entity.Problem;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MultipleChoiceProblemSaveRequest {
    private MultipleChoiceProblemRequest multipleChoiceProblemRequest;
    private ProblemCategory problemCategory;
    private ProblemType problemType;

    @Builder
    public MultipleChoiceProblemSaveRequest(MultipleChoiceProblemRequest multipleChoiceProblemRequest, ProblemCategory problemCategory, ProblemType problemType) {
        this.multipleChoiceProblemRequest = multipleChoiceProblemRequest;
        this.problemCategory = problemCategory;
        this.problemType = problemType;
    }


    public Problem toEntity(){
        return Problem.builder()
                .content(this.multipleChoiceProblemRequest.toEntity())
                .category(this.problemCategory)
                .type(this.problemType)
                .build();
    }

    @Override
    public String toString() {
        return "MultipleChoiceProblemSaveRequest{" +
                "multipleChoiceProblemRequest=" + multipleChoiceProblemRequest +
                ", problemCategory=" + problemCategory +
                ", problemType=" + problemType +
                '}';
    }
}
