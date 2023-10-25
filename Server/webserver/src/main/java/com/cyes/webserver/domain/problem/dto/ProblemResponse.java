package com.cyes.webserver.domain.problem.dto;


import com.cyes.webserver.domain.problem.dto.problemcontent.response.ProblemContentResponse;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemResponse {

    private String id;
    private ProblemContentResponse contentResponse;
    private ProblemCategory category;
    private ProblemType type;

    @Builder
    public ProblemResponse(String id, ProblemContentResponse contentResponse, String category, String type) {
        this.id = id;
        this.contentResponse = contentResponse;
        this.category = ProblemCategory.valueOf(category);
        this.type = ProblemType.valueOf(type);
    }
}
