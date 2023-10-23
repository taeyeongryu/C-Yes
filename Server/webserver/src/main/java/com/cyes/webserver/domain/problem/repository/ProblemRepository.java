package com.cyes.webserver.domain.problem.repository;

import com.cyes.webserver.domain.problem.entity.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends MongoRepository<Problem,String> {

}
