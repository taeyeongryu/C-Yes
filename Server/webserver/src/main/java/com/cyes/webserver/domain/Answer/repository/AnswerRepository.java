package com.cyes.webserver.domain.Answer.repository;

import com.cyes.webserver.domain.Answer.entity.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends MongoRepository<Answer,String> {
}
