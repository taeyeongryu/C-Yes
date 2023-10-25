package com.cyes.webserver.domain.problem.dto.problemcontent.request;

import com.cyes.webserver.domain.problem.dto.problemcontent.response.ProblemContentResponse;
import com.cyes.webserver.domain.problem.entity.ProblemContent;
import com.cyes.webserver.domain.problem.entity.ShortAnswer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortAnswerProblemRequest {
    //문제
    String question;
    //답
    String answer;

    @Builder
    public ShortAnswerProblemRequest(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public ProblemContent toEntity() {
        return ShortAnswer.builder()
                .question(this.question)
                .answer(this.answer).build();
    }
}
