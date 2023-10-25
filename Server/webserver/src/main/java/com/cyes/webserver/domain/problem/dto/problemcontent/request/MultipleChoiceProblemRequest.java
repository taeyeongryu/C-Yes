package com.cyes.webserver.domain.problem.dto.problemcontent.request;

import com.cyes.webserver.domain.problem.dto.problemcontent.response.ProblemContentResponse;
import com.cyes.webserver.domain.problem.entity.MultipleChoice;
import com.cyes.webserver.domain.problem.entity.ProblemContent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MultipleChoiceProblemRequest {
    //문제
    String question;
    //답
    String answer;
    //보기
    String[] choices;

    @Override
    public String toString() {
        return "MultipleChoiceProblemRequest{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", choices=" + Arrays.toString(choices) +
                '}';
    }

    @Builder
    public MultipleChoiceProblemRequest(String question, String answer, String[] choices) {
        this.question = question;
        this.answer = answer;
        this.choices = choices;
    }


    public ProblemContent toEntity() {
        return MultipleChoice.builder()
                .question(this.question)
                .answer(this.answer)
                .choices(this.choices).build();
    }
}
