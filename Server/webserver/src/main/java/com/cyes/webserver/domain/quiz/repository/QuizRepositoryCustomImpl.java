package com.cyes.webserver.domain.quiz.repository;

import com.cyes.webserver.domain.quiz.entity.QQuiz;
import com.cyes.webserver.domain.quiz.entity.Quiz;
import com.querydsl.core.types.Order;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
public class QuizRepositoryCustomImpl implements QuizRepositoryCustom {


    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Optional<Quiz> findLiveQuiz(LocalDateTime nowDateTime) {
        QQuiz quiz = QQuiz.quiz;
        
        LocalDateTime todayEnd = nowDateTime.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);

        return Optional.ofNullable(jpaQueryFactory
                .select(quiz)
                .from(quiz)
                .where(quiz.startDateTime.between(nowDateTime, todayEnd))
                .orderBy(quiz.startDateTime.asc())
                .fetchFirst());
    }
}
