package com.cyes.webserver.domain.problem.repository;


import com.cyes.webserver.domain.problem.entity.Problem;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

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
    void save() {
        // given
        Problem problem = Problem.builder()
                .content("문제 내용")
                .answer("문제 답안")
                .problemCategory(ProblemCategory.DB)
                .problemType(ProblemType.SHORTANSWER)
                .build();
        // when
        problemRepository.save(problem);

        // then
        assertThat(problem.getId()).isNotNull();
        assertThat(problemRepository.findAll()).isNotNull();
    }
    @Test
    @DisplayName("카테고리, Pabeable를 조건으로 DB에서 조회한다.")
    void findProblemByCategory() {
        // given
        insertProblem();
        Pageable pageable1 = PageRequest.of(0, 3); // page는 0-based index입니다.
        Pageable pageable2 = PageRequest.of(1, 3); // page는 0-based index입니다.
        Pageable pageable3 = PageRequest.of(2, 3); // page는 0-based index입니다.
        Pageable pageable4 = PageRequest.of(3, 3); // page는 0-based index입니다.
        // when
        Page<Problem> problemByCategory1 = problemRepository.findProblemByCategory(ProblemCategory.DB, pageable1);
        Page<Problem> problemByCategory2 = problemRepository.findProblemByCategory(ProblemCategory.DB, pageable2);
        Page<Problem> problemByCategory3 = problemRepository.findProblemByCategory(ProblemCategory.DB, pageable3);
        Page<Problem> problemByCategory4 = problemRepository.findProblemByCategory(ProblemCategory.DB, pageable4);
        // then
        assertThat(problemByCategory1.getContent()).hasSize(3)
                .extracting("content", "answer", "category")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("문제 내용0", "문제 답안0", ProblemCategory.DB),
                        Tuple.tuple("문제 내용1", "문제 답안1", ProblemCategory.DB),
                        Tuple.tuple("문제 내용2", "문제 답안2", ProblemCategory.DB)
                );
        assertThat(problemByCategory2.getContent()).hasSize(3)
                .extracting("content", "answer", "category")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("문제 내용3", "문제 답안3", ProblemCategory.DB),
                        Tuple.tuple("문제 내용4", "문제 답안4", ProblemCategory.DB),
                        Tuple.tuple("문제 내용5", "문제 답안5", ProblemCategory.DB)
                );

        assertThat(problemByCategory3.getContent()).hasSize(3)
                .extracting("content", "answer", "category")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("문제 내용6", "문제 답안6", ProblemCategory.DB),
                        Tuple.tuple("문제 내용7", "문제 답안7", ProblemCategory.DB),
                        Tuple.tuple("문제 내용8", "문제 답안8", ProblemCategory.DB)
                );
        assertThat(problemByCategory4.getContent()).hasSize(1)
                .extracting("content", "answer", "category")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("문제 내용9", "문제 답안9", ProblemCategory.DB)
                );
    }

    @Test
    @DisplayName("타입, Pabeable를 조건으로 DB에서 조회한다.")
    void findProblemByType() {
        // given
        insertProblem();
        Pageable pageable1 = PageRequest.of(0, 3); // page는 0-based index입니다.
        Pageable pageable2 = PageRequest.of(1, 3); // page는 0-based index입니다.
        Pageable pageable3 = PageRequest.of(2, 3); // page는 0-based index입니다.
        Pageable pageable4 = PageRequest.of(3, 3); // page는 0-based index입니다.
        // when
        Page<Problem> problemByCategory1 = problemRepository.findProblemByType(ProblemType.SHORTANSWER, pageable1);
        Page<Problem> problemByCategory2 = problemRepository.findProblemByType(ProblemType.SHORTANSWER, pageable2);
        Page<Problem> problemByCategory3 = problemRepository.findProblemByType(ProblemType.SHORTANSWER, pageable3);
        Page<Problem> problemByCategory4 = problemRepository.findProblemByType(ProblemType.SHORTANSWER, pageable4);


        // then
        assertThat(problemByCategory1.getContent()).hasSize(3)
                .extracting("content", "answer", "category","type")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("문제 내용0", "문제 답안0", ProblemCategory.DB,ProblemType.SHORTANSWER),
                        Tuple.tuple("문제 내용1", "문제 답안1", ProblemCategory.DB,ProblemType.SHORTANSWER),
                        Tuple.tuple("문제 내용2", "문제 답안2", ProblemCategory.DB,ProblemType.SHORTANSWER)
                );
        assertThat(problemByCategory2.getContent()).hasSize(3)
                .extracting("content", "answer", "category","type")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("문제 내용3", "문제 답안3", ProblemCategory.DB,ProblemType.SHORTANSWER),
                        Tuple.tuple("문제 내용4", "문제 답안4", ProblemCategory.DB,ProblemType.SHORTANSWER),
                        Tuple.tuple("문제 내용5", "문제 답안5", ProblemCategory.DB,ProblemType.SHORTANSWER)
                );

        assertThat(problemByCategory3.getContent()).hasSize(3)
                .extracting("content", "answer", "category","type")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("문제 내용6", "문제 답안6", ProblemCategory.DB,ProblemType.SHORTANSWER),
                        Tuple.tuple("문제 내용7", "문제 답안7", ProblemCategory.DB,ProblemType.SHORTANSWER),
                        Tuple.tuple("문제 내용8", "문제 답안8", ProblemCategory.DB,ProblemType.SHORTANSWER)
                );
        assertThat(problemByCategory4.getContent()).hasSize(3)
                .extracting("content", "answer", "category","type")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("문제 내용9", "문제 답안9", ProblemCategory.DB,ProblemType.SHORTANSWER)
                        ,Tuple.tuple("문제 내용20", "문제 답안20", ProblemCategory.OS,ProblemType.SHORTANSWER)
                        ,Tuple.tuple("문제 내용21", "문제 답안21", ProblemCategory.OS,ProblemType.SHORTANSWER)
                );
    }

    @Test
    @DisplayName("카테고리, 타입, Pabeable를 조건으로 DB에서 조회한다.")
    void findProblemByCategoryAndType() {
        // given
        insertProblem();
        Pageable pageable1 = PageRequest.of(0, 3); // page는 0-based index입니다.
        Pageable pageable2 = PageRequest.of(1, 3); // page는 0-based index입니다.
        Pageable pageable3 = PageRequest.of(2, 3); // page는 0-based index입니다.
        Pageable pageable4 = PageRequest.of(3, 3); // page는 0-based index입니다.
        // when
        Page<Problem> problemByCategory1 = problemRepository.findProblemByCategoryAndType(ProblemCategory.OS,ProblemType.SHORTANSWER, pageable1);
        Page<Problem> problemByCategory2 = problemRepository.findProblemByCategoryAndType(ProblemCategory.OS,ProblemType.SHORTANSWER, pageable2);
        Page<Problem> problemByCategory3 = problemRepository.findProblemByCategoryAndType(ProblemCategory.OS,ProblemType.SHORTANSWER, pageable3);
        Page<Problem> problemByCategory4 = problemRepository.findProblemByCategoryAndType(ProblemCategory.OS,ProblemType.SHORTANSWER, pageable4);


        // then
        assertThat(problemByCategory1.getContent()).hasSize(3)
                .extracting("content", "answer", "category","type")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("문제 내용20", "문제 답안20", ProblemCategory.OS,ProblemType.SHORTANSWER),
                        Tuple.tuple("문제 내용21", "문제 답안21", ProblemCategory.OS,ProblemType.SHORTANSWER),
                        Tuple.tuple("문제 내용22", "문제 답안22", ProblemCategory.OS,ProblemType.SHORTANSWER)
                );
        assertThat(problemByCategory2.getContent()).hasSize(3)
                .extracting("content", "answer", "category","type")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("문제 내용23", "문제 답안23", ProblemCategory.OS,ProblemType.SHORTANSWER),
                        Tuple.tuple("문제 내용24", "문제 답안24", ProblemCategory.OS,ProblemType.SHORTANSWER),
                        Tuple.tuple("문제 내용25", "문제 답안25", ProblemCategory.OS,ProblemType.SHORTANSWER)
                );

        assertThat(problemByCategory3.getContent()).hasSize(3)
                .extracting("content", "answer", "category","type")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("문제 내용26", "문제 답안26", ProblemCategory.OS,ProblemType.SHORTANSWER),
                        Tuple.tuple("문제 내용27", "문제 답안27", ProblemCategory.OS,ProblemType.SHORTANSWER),
                        Tuple.tuple("문제 내용28", "문제 답안28", ProblemCategory.OS,ProblemType.SHORTANSWER)
                );
        assertThat(problemByCategory4.getContent()).hasSize(1)
                .extracting("content", "answer", "category","type")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("문제 내용29", "문제 답안29", ProblemCategory.OS,ProblemType.SHORTANSWER)
                );
    }

    private void insertProblem(){
        List<Problem> list = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            Problem problem = Problem.builder().content("문제 내용"+i).answer("문제 답안"+i).problemCategory(ProblemCategory.DB).problemType(ProblemType.SHORTANSWER).build();
            list.add(problem);
        }
        for (int i = 10; i < 20; i++) {
            Problem problem = Problem.builder().content("문제 내용"+i).answer("문제 답안"+i).problemCategory(ProblemCategory.NETWORK).problemType(ProblemType.MULTIPLECHOICE).build();
            list.add(problem);
        }
        for (int i = 20; i < 30; i++) {
            Problem problem = Problem.builder().content("문제 내용"+i).answer("문제 답안"+i).problemCategory(ProblemCategory.OS).problemType(ProblemType.SHORTANSWER).build();
            list.add(problem);
        }
        problemRepository.saveAll(list);
    }
}