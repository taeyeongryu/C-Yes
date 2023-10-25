package com.cyes.webserver.domain.problem.entity;

import com.cyes.webserver.domain.problem.dto.problemcontent.response.MultipleChoiceProblemContentResponse;
import com.cyes.webserver.domain.problem.dto.problemcontent.response.ProblemContentResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MultipleChoice implements ProblemContent{
    //pk
    @Id
    private String id;

    private String question;

    private String[] choices;

    private String answer;


    @Override
    public ProblemContentResponse toProblemContentResponse() {
        return new MultipleChoiceProblemContentResponse(this.question, this.answer, this.choices);
    }
    @Builder
    public MultipleChoice(String id, String question, String[] choices, String answer) {
        this.id = id;
        this.question = question;
        this.choices = choices;
        this.answer = answer;
    }
}
