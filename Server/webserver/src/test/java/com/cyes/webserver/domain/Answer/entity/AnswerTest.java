package com.cyes.webserver.domain.Answer.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class AnswerTest {
    @Test
    @DisplayName("Answer Entity 를 생성한다.")
    void answerCreateTest() {
        // given
        String id = "pk가 들어갈 값입니다.";
        Long memberId = 1L;
        Long quizId = 2L;
        Integer problemNumber = 3;
        String submitContent = "제출 답안";

        // when
        Answer answer = Answer.builder()
                .id(id)
                .memberId(memberId)
                .quizId(quizId)
                .problemNumber(problemNumber)
                .submitContent(submitContent)
                .build();
        // then
        assertThat(answer).extracting("id", "memberId", "quizId", "problemNumber", "submitContent")
                .containsExactly(id, memberId, quizId, problemNumber, submitContent);
    }
}