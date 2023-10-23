package com.cyes.webserver.domain.quizproblem.repository;

import com.cyes.webserver.domain.quizproblem.entity.QuizProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizProblemRepository extends JpaRepository<QuizProblem,Long> {
}
