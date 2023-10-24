package com.cyes.webserver.domain.quizproblem.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuizProblemRepositoryCustomImpl {

    private final JPAQueryFactory jpaQueryFactory;




}
