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

        // 퀴즈 정보 조회
        Quiz quiz = quizRepository.findLiveQuiz().orElseThrow(() -> new CustomException(CustomExceptionList.QUIZ_NOT_FOUND_ERROR));

        // Entity -> Dto
        QuizInfoResponse quizInfoResponse = quiz.toQuizInfoResponse();

        return quizInfoResponse;

    }

}
