package com.cyes.webserver.domain.quiz.repository;

import com.cyes.webserver.domain.quiz.entity.QQuiz;
import com.cyes.webserver.domain.quiz.entity.Quiz;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuizRepositoryCustomImpl implements QuizRepositoryCustom {


    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Optional<Quiz> findAllQuiz() {

        QQuiz quiz = QQuiz.quiz;

        return Optional.ofNullable(jpaQueryFactory
                .select(quiz)
                .from(quiz)
                .fetchOne());

    }
}
