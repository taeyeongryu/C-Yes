package com.cyes.webserver.domain.quiz.service;

import com.cyes.webserver.domain.quiz.dto.QuizInfoResponse;
import com.cyes.webserver.domain.quiz.entity.Quiz;
import com.cyes.webserver.domain.quiz.repository.QuizRepository;
import com.cyes.webserver.exception.CustomException;
import com.cyes.webserver.exception.CustomExceptionList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizInfoResponse search() {

        try {
            Quiz quiz = quizRepository.findAllQuiz().orElseThrow(() -> new CustomException(CustomExceptionList.MEMBER_NOT_FOUND_ERROR));

            QuizInfoResponse quizInfoResponse = QuizInfoResponse.builder()
                    .quizTitle(quiz.getTitle())
                    .quizLink(quiz.getLink())
                    .build();

            return quizInfoResponse;

        } catch (Exception e) {
            throw new CustomException(CustomExceptionList.INTERNAL_SERVER_ERROR);
        }
    }

}
