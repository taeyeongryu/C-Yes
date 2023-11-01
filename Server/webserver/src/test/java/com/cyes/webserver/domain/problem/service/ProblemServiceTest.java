//package com.cyes.webserver.domain.problem.service;
//
//import com.cyes.webserver.domain.problem.dto.ProblemResponse;
//import com.cyes.webserver.domain.problem.entity.ProblemCategory;
//import com.cyes.webserver.domain.problem.entity.ProblemType;
//import org.assertj.core.groups.Tuple;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//@SpringBootTest
//class ProblemServiceTest {
//    @Autowired
//    private ProblemService problemService;
//
//
//    @Test
//    @DisplayName("문제 Id List로 문제를 조회할 수 있다.")
//    void findAllProblemByQuiz() {
//        // given
//        List<String> idList = List.of("6539cc2767256908d1e90229","6539cc3667256908d1e9022a","6539cc4567256908d1e9022c");
//        // when
//        List<ProblemResponse> allProblemByQuiz = problemService.findAllProblemByQuiz(idList);
//        System.out.println("allProblemByQuiz = " + allProblemByQuiz);
//        // then
//
//        assertThat(allProblemByQuiz).hasSize(1)
//                .extracting("id", "contentResponse.question", "contentResponse.answer", "category", "type").containsExactly(
//                        Tuple.tuple("6539cc2767256908d1e90229", "컴퓨터에서 실행 중인 프로그램 또는 작업을 무엇이라고 합니까?", "프로세스", ProblemCategory.OS, ProblemType.SHORTANSWER));
//
//    }
//
//}