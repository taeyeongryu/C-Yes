package com.cyes.webserver.domain.problem.repository;


import com.cyes.webserver.domain.problem.entity.Problem;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ProblemRepositoryTest {
    @Autowired
    private ProblemRepository problemRepository;

    @AfterEach
    void tearDown() {
        problemRepository.deleteAll();
    }
    @Test
    @DisplayName("문제를 DB에 저장한다.")
    void saveTest() {
        // given
        Problem problem = Problem.builder()
                .content("문제 내용")
                .answer("문제 답안")
                .problemCategory(ProblemCategory.DB)
                .problemType(ProblemType.SHORTANSWER)
                .build();
        // when
        problemRepository.save(problem);

//        System.out.println("problem.getId() = " + problem.getId());

        // then
        assertThat(problem.getId()).isNotNull();
        assertThat(problemRepository.findAll()).isNotNull();
    }
}