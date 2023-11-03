package com.cyes.webserver.domain.adminQuiz.repository;

import com.cyes.webserver.domain.adminQuiz.entity.noCheckShortProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface noCheckProblemRepository extends MongoRepository<noCheckShortProblem, String> {
}
