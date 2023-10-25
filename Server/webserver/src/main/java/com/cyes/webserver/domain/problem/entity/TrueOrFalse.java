package com.cyes.webserver.domain.problem.entity;

import com.cyes.webserver.domain.problem.dto.problemcontent.response.ProblemContentResponse;
import com.cyes.webserver.domain.problem.dto.problemcontent.response.TrueOrFalseProblemContentResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TrueOrFalse implements ProblemContent{
    //pk
    @Id
    private String id;
    private String question;
    private String answer;


    @Override
    public ProblemContentResponse toProblemContentResponse() {
        return TrueOrFalseProblemContentResponse.builder()
                .question(this.question)
                .answer(this.answer)
                .build();
    }

    @Builder
    public TrueOrFalse(String id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }


}
