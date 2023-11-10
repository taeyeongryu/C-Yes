package com.cyes.webserver.domain.problem.dto.request;


import com.cyes.webserver.domain.problem.entity.ProblemByUser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemSaveByUserRequest {
    private String question;
    private String[] choices;
    private String answer;
    private String category;
    private String type;

    @Builder
    public ProblemSaveByUserRequest(String question, String[] choices, String answer, String category, String type) {
        this.question = question;
        this.choices = choices;
        this.answer = answer;
        this.category = category;
        this.type = type;
    }
    public ProblemByUser toDocument(){
        return ProblemByUser.builder()
                .question(this.question)
                .choices(this.choices)
                .answer(this.answer)
                .category(this.category)
                .type(this.type)
                .build();
    }
}
