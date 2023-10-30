package com.cyes.webserver.domain.Answer.dto;

import com.cyes.webserver.domain.Answer.entity.Answer;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerSaveControllerRequest {
    private Long memberId;
    private Long quizId;
    private Integer problemNumber;
    private String submitContent;

    @Builder
    public AnswerSaveControllerRequest(Long memberId, Long quizId, Integer problemNumber, String submitContent) {
        this.memberId = memberId;
        this.quizId = quizId;
        this.problemNumber = problemNumber;
        this.submitContent = submitContent;
    }

    public AnswerSaveServiceRequest toServiceRequest(){
        return AnswerSaveServiceRequest.builder()
                .memberId(this.memberId)
                .quizId(this.quizId)
                .problemNumber(this.problemNumber)
                .submitContent(this.submitContent).build();
    }
}
