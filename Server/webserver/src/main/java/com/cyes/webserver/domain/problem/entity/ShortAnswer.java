package com.cyes.webserver.domain.problem.entity;

import com.cyes.webserver.domain.problem.dto.problemcontent.response.ProblemContentResponse;
import com.cyes.webserver.domain.problem.dto.problemcontent.response.ShortAnswerProblemContentResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortAnswer implements ProblemContent{
    //pk
    @Id
    private String id;
    //문제
    private String question;
    //답
    private String answer;


    @Override
    public ProblemContentResponse toProblemContentResponse() {
        return ShortAnswerProblemContentResponse.builder()
                .question(this.question)
                .answer(this.answer)
                .build();
    }

    @Builder
    public ShortAnswer(String id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "ShortAnswer{" +
                "id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
