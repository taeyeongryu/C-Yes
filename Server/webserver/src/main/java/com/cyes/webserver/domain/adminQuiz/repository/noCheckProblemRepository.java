package com.cyes.webserver.domain.adminQuiz.repository;

import com.cyes.webserver.domain.adminQuiz.entity.noCheckShortProblem;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface noCheckProblemRepository extends MongoRepository<noCheckShortProblem, String> {
}
