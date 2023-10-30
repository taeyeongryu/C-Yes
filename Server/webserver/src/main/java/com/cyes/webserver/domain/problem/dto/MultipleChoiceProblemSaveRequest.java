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


    @Builder
    public MultipleChoiceProblemSaveRequest(MultipleChoiceProblemRequest multipleChoiceProblemRequest, ProblemCategory problemCategory) {
        this.multipleChoiceProblemRequest = multipleChoiceProblemRequest;
        this.problemCategory = problemCategory;

    }


    public Problem toEntity(){
        return Problem.builder()
                .content(this.multipleChoiceProblemRequest.toEntity())
                .category(this.problemCategory)
                .type(ProblemType.MULTIPLECHOICE)
                .build();
    }
}
