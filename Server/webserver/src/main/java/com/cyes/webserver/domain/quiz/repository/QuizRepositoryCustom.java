package com.cyes.webserver.domain.quiz.repository;

import com.cyes.webserver.domain.quiz.entity.Quiz;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface QuizRepositoryCustom {

    Optional<Quiz> findLiveQuiz(LocalDateTime nowDateTime);
}
