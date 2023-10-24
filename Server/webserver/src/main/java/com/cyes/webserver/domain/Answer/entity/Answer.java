package com.cyes.webserver.domain.Answer.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {
    //pk
    @Id
    private String id;

    //답안 제출자
    private Long memberId;

    //quiz pk
    private Long quizId;

    //퀴즈 내에서 문제 번호
    private Integer problemNumber;

    //제출 답안
    private String submitContent;

    @Builder
    public Answer(String id, Long memberId, Long quizId, Integer problemNumber, String submitContent) {
        this.id = id;
        this.memberId = memberId;
        this.quizId = quizId;
        this.problemNumber = problemNumber;
        this.submitContent = submitContent;
    }
}
