package com.cyes.webserver.domain.problem.dto;


import com.cyes.webserver.domain.problem.dto.problemcontent.response.ProblemContentResponse;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.IOException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemResponse {

    private String id;
    private ProblemContentResponse contentResponse;
    private Integer problemOrder;
    private ProblemCategory category;
    private ProblemType type;

    @Builder
    public ProblemResponse(String id, Integer problemOrder, ProblemContentResponse contentResponse, String category, String type) {
        this.id = id;
        this.problemOrder = problemOrder;
        this.contentResponse = contentResponse;
        this.category = ProblemCategory.valueOf(category);
        this.type = ProblemType.valueOf(type);
    }

    public ProblemResponse(String jsonString) {
        // jsonString을 파싱하여 필드를 초기화하는 로직을 추가
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ProblemResponse temp = objectMapper.readValue(jsonString, ProblemResponse.class);
            this.id = temp.id;
            this.contentResponse = temp.contentResponse;
            this.category = temp.category;
            this.type = temp.type;
            // 필드 추가 및 초기화
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "ProblemResponse{" +
                "id='" + id + '\'' +
                ", contentResponse=" + contentResponse +
                ", category=" + category +
                ", type=" + type +
                '}';
    }
}
