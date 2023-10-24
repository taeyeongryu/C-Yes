package com.cyes.webserver.domain.Answer.repository;

import com.cyes.webserver.domain.Answer.entity.Answer;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;


public interface AnswerRepository extends MongoRepository<Answer,String> {
    @Query("{ 'member_id' : ?0, 'quiz_id' : ?1 }")
    List<Answer> findAnswerByMemberIdAndQuizId(Long memberId, Long quizId, Sort sort);
}
