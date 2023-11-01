package com.cyes.webserver.domain.problem.entity;

import com.cyes.webserver.domain.problem.dto.ProblemResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;



@Getter
@Document(collection = "problem")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem {

    //pk
    @Id
    private String id;

    @DBRef
    @Field(name = "problem_content")
    private ProblemContent content;

    //카테고리(네트워크, 운영체제 등등)
    //인덱싱으로 조회 성능 향상
    @Indexed
    @Field(name = "problem_category")
    private String category;

    //문제유형 객관식, 단답형 등등
    //인덱싱으로 조회 성능 향상
    @Indexed
    @Field(name = "problem_type")
    private String type;

    @Builder
    public Problem(String id, ProblemContent content, ProblemCategory category, ProblemType type) {
        this.id = id;
        this.content = content;
        this.category = String.valueOf(category);
        this.type = String.valueOf(type);
    }

    public ProblemCategory getCategory(){
        return ProblemCategory.valueOf(this.category);
    }
    public ProblemType getType(){
        return ProblemType.valueOf(this.type);
    }



    public ProblemResponse toProblemResponse(){
        return ProblemResponse.builder()
                .id(this.id)
                .contentResponse(this.content.toProblemContentResponse())
                .category(this.category)
                .type(this.type)
                .build();
    }

    public ProblemResponse toProblemResponse(int problemOrder){
        ProblemResponse problemResponse = ProblemResponse.builder()
                .id(this.id)
                .contentResponse(this.content.toProblemContentResponse())
                .problemOrder(problemOrder)
                .category(this.category)
                .type(this.type)
                .build();
        return problemResponse;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "id='" + id + '\'' +
                ", content=" + content +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
//        public void changeByUpdateDto(ProblemUpdateServiceRequest problemUpdateServiceRequest){
//        this.content = problemUpdateServiceRequest.getContent();
//        this.answer = problemUpdateServiceRequest.getAnswer();
//        this.category =String.valueOf(problemUpdateServiceRequest.getProblemCategory());
//        this.type = String.valueOf(problemUpdateServiceRequest.getProblemType());
//        return;
//    }
}
