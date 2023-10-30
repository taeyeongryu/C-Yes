package com.cyes.webserver.domain.problem.dto;

import com.cyes.webserver.domain.problem.dto.problemcontent.request.ShortAnswerProblemRequest;
import com.cyes.webserver.domain.problem.entity.Problem;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortAnswerProblemSaveRequest {
    private ShortAnswerProblemRequest shortAnswerProblemRequest;
    private ProblemCategory problemCategory;


    @Builder
    public ShortAnswerProblemSaveRequest(ShortAnswerProblemRequest shortAnswerProblemRequest, ProblemCategory problemCategory) {
        this.shortAnswerProblemRequest = shortAnswerProblemRequest;
        this.problemCategory = problemCategory;
    }

    public Problem toEntity(){
        return Problem.builder()
                .content(this.shortAnswerProblemRequest.toEntity())
                .category(this.problemCategory)
                .type(ProblemType.SHORTANSWER)
                .build();
    }
}
