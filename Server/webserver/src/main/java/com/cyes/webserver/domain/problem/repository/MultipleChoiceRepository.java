package com.cyes.webserver.domain.problem.repository;

import com.cyes.webserver.domain.problem.entity.MultipleChoice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MultipleChoiceRepository extends MongoRepository<MultipleChoice,String> {
}
