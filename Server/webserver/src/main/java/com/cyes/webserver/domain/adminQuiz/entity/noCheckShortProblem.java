package com.cyes.webserver.domain.adminQuiz.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class adminShortProblem {
    //pk
    @Id
    private String id;
    //문제
    private String question;
    //답
    private String answer;
}
