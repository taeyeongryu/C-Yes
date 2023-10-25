package com.cyes.webserver.domain.problem.repository;

import com.cyes.webserver.domain.problem.entity.ShortAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShortAnswerRepository extends MongoRepository<ShortAnswer,String> {
}
