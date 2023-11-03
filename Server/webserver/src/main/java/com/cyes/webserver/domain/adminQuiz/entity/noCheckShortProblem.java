package com.cyes.webserver.domain.adminQuiz.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Getter
@Document(collection = "noCheckShortProblem")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class noCheckShortProblem {
    //pk
    @Id
    private String id;
    //문제
    @Indexed
    @Field(name = "question")
    private String question;

    @Builder
    private noCheckShortProblem (String question) {
        this.question = question;

    }

    public void saveproblem(String content){
        this.question = content;
    }

}
