package com.cyes.webserver.domain.problem.repository;

import com.cyes.webserver.domain.problem.entity.TrueOrFalse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrueOrFalseRepository extends MongoRepository<TrueOrFalse,String> {
}
