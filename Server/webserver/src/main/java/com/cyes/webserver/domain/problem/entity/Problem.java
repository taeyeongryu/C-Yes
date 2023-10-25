package com.cyes.webserver.domain.problem.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;



@Document(collection = "problem")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Problem {

    //pk
    @Id
    private String id;

    //내용
//    @Field(name = "problem_content")
    private String content;

    //정답
//    @Field(name = "problem_answer")
    private String answer;

    //카테고리(네트워크, 운영체제 등등)
//    @Field(name = "problem_category")
    private String category;

    //문제유형 객관식, 단답형 등등
//    @Field(name = "problem_type")
    private String type;

    @Builder
    public Problem(String id, String content, String answer, ProblemCategory problemCategory, ProblemType problemType) {
        this.id = id;
        this.content = content;
        this.answer = answer;
        this.category = String.valueOf(problemCategory);
        this.type = String.valueOf(problemType);
    }
    public ProblemCategory getCategory(){
        return ProblemCategory.valueOf(this.category);
    }
    public ProblemType getType(){
        return ProblemType.valueOf(this.type);
    }
}
