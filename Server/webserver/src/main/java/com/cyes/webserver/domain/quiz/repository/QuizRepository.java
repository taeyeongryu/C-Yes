package com.cyes.webserver.domain.quiz.repository;

import com.cyes.webserver.domain.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long>,QuizRepositoryCustom {

}
