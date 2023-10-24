package com.cyes.webserver.domain.problem.repository;

import com.cyes.webserver.domain.problem.dto.ProblemResponse;
import com.cyes.webserver.domain.problem.entity.Problem;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

//@EnableMongoRepositories
public interface ProblemRepository extends MongoRepository<Problem,String> {
    @Query("{ 'category' : ?0 }")
    Page<Problem> findProblemByCategory(ProblemCategory category, Pageable pageable);

    @Query("{ 'type' : ?0 }")
    Page<Problem> findProblemByType(ProblemType type, Pageable pageable);

    @Query("{ 'category' : ?0, 'type' : ?1 }")
    Page<Problem> findProblemByCategoryAndType(ProblemCategory category, ProblemType type, Pageable pageable);

}
