package com.cyes.webserver.domain.quiz.controller;

import com.cyes.webserver.domain.quiz.dto.QuizInfoResponse;
import com.cyes.webserver.domain.quiz.service.QuizService;
import com.cyes.webserver.exception.CustomException;
import com.cyes.webserver.exception.CustomExceptionList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;


    @GetMapping("/live/info")
    private ResponseEntity<QuizInfoResponse> getQuizInfo() {

        try {
            QuizInfoResponse quizInfoResponse = quizService.search();
            return ResponseEntity.status(HttpStatus.OK).body(quizInfoResponse);
        } catch (CustomException e) {

            return ResponseEntity.status(e.getException().getStatus()).body(null);

        }
    }

}
