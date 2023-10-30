package com.cyes.webserver.domain.Answer.entity;

import com.cyes.webserver.domain.Answer.dto.AnswerResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.naming.Name;

@Document
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {
    //pk
    @Id
    private String id;

    //답안 제출자
    @Indexed
    @Field(name = "member_id")
    private Long memberId;

    //quiz pk
    @Indexed
    @Field(name = "quiz_id")
    private Long quizId;

    //퀴즈 내에서 문제 번호
    @Field(name = "problem_number")
    private Integer problemNumber;

    //제출 답안
    @Field(name = "submit_content")
    private String submitContent;

    @Builder
    public Answer(String id, Long memberId, Long quizId, Integer problemNumber, String submitContent) {
        this.id = id;
        this.memberId = memberId;
        this.quizId = quizId;
        this.problemNumber = problemNumber;
        this.submitContent = submitContent;
    }

    public AnswerResponse toAnswerResponse(){
        AnswerResponse answerResponse = AnswerResponse.builder()
                .id(this.id)
                .memberId(this.memberId)
                .quizId(this.quizId)
                .problemNumber(this.problemNumber)
                .submitContent(this.submitContent)
                .build();
        return answerResponse;
    }
}
