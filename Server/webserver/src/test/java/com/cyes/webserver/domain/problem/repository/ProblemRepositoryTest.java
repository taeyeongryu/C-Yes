package com.cyes.webserver.domain.problem.repository;


import com.cyes.webserver.domain.problem.entity.*;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import static org.assertj.core.api.Assertions.assertThat;


//@SpringBootTest
@DataMongoTest
class ProblemRepositoryTest {
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private MultipleChoiceRepository multipleChoiceRepository;
    @Autowired
    private ShortAnswerRepository shortAnswerRepository;
    @Autowired
    private TrueOrFalseRepository trueOrFalseRepository;



    @AfterEach
    void tearDown() {
        problemRepository.deleteAll();
        multipleChoiceRepository.deleteAll();
        shortAnswerRepository.deleteAll();
        trueOrFalseRepository.deleteAll();

    }
    @Test
    @DisplayName("문제를 DB에 저장한다.")
    void save() {
        // given
        MultipleChoice multipleChoice = MultipleChoice.builder()
                .question("question")
                .choices(new String[]{"choice1", "choice2", "choice3", "choice4"})
                .answer("answer")
                .build();


        multipleChoiceRepository.save(multipleChoice);

        Problem problem = Problem.builder()
                .content(multipleChoice)
                .category(ProblemCategory.OS)
                .type(ProblemType.MULTIPLECHOICE)
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
        insertProblemContent();
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
                .extracting(problem -> problem.getContent().toProblemContentResponse().getQuestion()
                        ,problem -> problem.getContent().toProblemContentResponse().getAnswer()
                        ,Problem::getCategory
                        ,Problem::getType)
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question11", "answer11", ProblemCategory.DB, ProblemType.TRUEORFALSE),
                        Tuple.tuple("question12", "answer12", ProblemCategory.DB, ProblemType.TRUEORFALSE),
                        Tuple.tuple("question13", "answer13", ProblemCategory.DB, ProblemType.TRUEORFALSE)
                );

    }

    @Test
    @DisplayName("타입, Pabeable를 조건으로 DB에서 조회한다.")
    void findProblemByType() {
        // given
        insertProblemContent();
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
                .extracting(
                        problem->problem.getContent().toProblemContentResponse().getQuestion()
                        ,problem->problem.getContent().toProblemContentResponse().getAnswer()
                        ,Problem::getCategory
                        ,Problem::getType
                )
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question21", "answer21", ProblemCategory.NETWORK, ProblemType.SHORTANSWER),
                        Tuple.tuple("question22", "answer22", ProblemCategory.NETWORK, ProblemType.SHORTANSWER),
                        Tuple.tuple("question23", "answer23", ProblemCategory.NETWORK, ProblemType.SHORTANSWER)
                );
        assertThat(problemByCategory2.getContent()).hasSize(3)
                .extracting(
                        problem -> problem.getContent().toProblemContentResponse().getQuestion()
                        , problem -> problem.getContent().toProblemContentResponse().getAnswer()
                        , Problem::getCategory
                        , Problem::getType
                )
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question24", "answer24", ProblemCategory.NETWORK, ProblemType.SHORTANSWER),
                        Tuple.tuple("question25", "answer25", ProblemCategory.NETWORK, ProblemType.SHORTANSWER),
                        Tuple.tuple("question26", "answer26", ProblemCategory.NETWORK, ProblemType.SHORTANSWER)
                );
        assertThat(problemByCategory3.getContent()).hasSize(3)
                .extracting(
                        problem -> problem.getContent().toProblemContentResponse().getQuestion()
                        , problem -> problem.getContent().toProblemContentResponse().getAnswer()
                        , Problem::getCategory
                        , Problem::getType
                )
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question27", "answer27", ProblemCategory.NETWORK, ProblemType.SHORTANSWER),
                        Tuple.tuple("question28", "answer28", ProblemCategory.NETWORK, ProblemType.SHORTANSWER),
                        Tuple.tuple("question29", "answer29", ProblemCategory.NETWORK, ProblemType.SHORTANSWER)
                );
        assertThat(problemByCategory4.getContent()).hasSize(1)
                .extracting(
                        problem -> problem.getContent().toProblemContentResponse().getQuestion()
                        , problem -> problem.getContent().toProblemContentResponse().getAnswer()
                        , Problem::getCategory
                        , Problem::getType
                )
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question30", "answer30", ProblemCategory.NETWORK, ProblemType.SHORTANSWER)
                );

    }

    @Test
    @DisplayName("카테고리, 타입, Pabeable를 조건으로 DB에서 조회한다.")
    void findProblemByCategoryAndType() {
        // given
        insertProblemContent();
        Pageable pageable1 = PageRequest.of(0, 3); // page는 0-based index입니다.
        Pageable pageable2 = PageRequest.of(0, 3); // page는 0-based index입니다.
        Pageable pageable3 = PageRequest.of(0, 3); // page는 0-based index입니다.
        // when
        Page<Problem> problemByCategory1 = problemRepository.findProblemByCategoryAndType(ProblemCategory.OS,ProblemType.MULTIPLECHOICE, pageable1);
        Page<Problem> problemByCategory2 = problemRepository.findProblemByCategoryAndType(ProblemCategory.DB,ProblemType.TRUEORFALSE, pageable2);
        Page<Problem> problemByCategory3 = problemRepository.findProblemByCategoryAndType(ProblemCategory.NETWORK,ProblemType.SHORTANSWER, pageable3);


        // then
        assertThat(problemByCategory1.getContent()).hasSize(3)
                .extracting(problem -> problem.getContent().toProblemContentResponse().getQuestion()
                        ,problem -> problem.getContent().toProblemContentResponse().getAnswer()
                        ,Problem::getCategory
                        ,Problem::getType)
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question1", "answer1", ProblemCategory.OS, ProblemType.MULTIPLECHOICE),
                        Tuple.tuple("question2", "answer2", ProblemCategory.OS, ProblemType.MULTIPLECHOICE),
                        Tuple.tuple("question3", "answer3", ProblemCategory.OS, ProblemType.MULTIPLECHOICE)
                );
        assertThat(problemByCategory2.getContent()).hasSize(3)
                .extracting(problem -> problem.getContent().toProblemContentResponse().getQuestion()
                        , problem -> problem.getContent().toProblemContentResponse().getAnswer()
                        , Problem::getCategory
                        , Problem::getType)
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question11", "answer11", ProblemCategory.DB, ProblemType.TRUEORFALSE),
                        Tuple.tuple("question12", "answer12", ProblemCategory.DB, ProblemType.TRUEORFALSE),
                        Tuple.tuple("question13", "answer13", ProblemCategory.DB, ProblemType.TRUEORFALSE)
                );

        assertThat(problemByCategory3.getContent()).hasSize(3)
                .extracting(problem -> problem.getContent().toProblemContentResponse().getQuestion()
                        , problem -> problem.getContent().toProblemContentResponse().getAnswer()
                        , Problem::getCategory
                        , Problem::getType)
                .containsExactlyInAnyOrder(
                        Tuple.tuple("question21", "answer21", ProblemCategory.NETWORK, ProblemType.SHORTANSWER),
                        Tuple.tuple("question22", "answer22", ProblemCategory.NETWORK, ProblemType.SHORTANSWER),
                        Tuple.tuple("question23", "answer23", ProblemCategory.NETWORK, ProblemType.SHORTANSWER)
                );
    }

    private void insertProblemContent(){
        for (int i = 1; i <=10 ; i++) {
            MultipleChoice multipleChoice = MultipleChoice.builder()
                    .question("question" + i)
                    .choices(new String[]{"choice1", "choice2", "choice3", "choice4"})
                    .answer("answer" + i)
                    .build();

            multipleChoiceRepository.save(multipleChoice);

            Problem problem = Problem.builder()
                    .content(multipleChoice)
                    .category(ProblemCategory.OS)
                    .type(ProblemType.MULTIPLECHOICE)
                    .build();
            problemRepository.save(problem);
        }
        for (int i = 11; i <= 20; i++) {
            TrueOrFalse trueOrFalse = TrueOrFalse.builder()
                    .question("question" + i)
                    .answer("answer" + i)
                    .build();

            trueOrFalseRepository.save(trueOrFalse);

            Problem problem = Problem.builder()
                    .content(trueOrFalse)
                    .category(ProblemCategory.DB)
                    .type(ProblemType.TRUEORFALSE)
                    .build();
            problemRepository.save(problem);
        }
        for (int i = 21; i <= 30; i++) {
            ShortAnswer shortAnswer = ShortAnswer.builder()
                    .question("question" + i)
                    .answer("answer" + i)
                    .build();

            shortAnswerRepository.save(shortAnswer);

            Problem problem = Problem.builder()
                    .content(shortAnswer)
                    .category(ProblemCategory.NETWORK)
                    .type(ProblemType.SHORTANSWER)
                    .build();
            problemRepository.save(problem);
        }
    }
}