package com.cyes.webserver.domain.problem.entity;

import com.mongodb.lang.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemByUser {
    //pk
    @Id
    private String id;

    @Field("problem_question")
    private String question;

    @Field("problem_choices")
    @Nullable
    private String[] choices;

    @Field("problem_answer")
    private String answer;

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
}
