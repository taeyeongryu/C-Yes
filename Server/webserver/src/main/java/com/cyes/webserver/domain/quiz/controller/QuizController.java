package com.cyes.webserver.domain.quiz.controller;

import com.cyes.webserver.domain.quiz.dto.*;
import com.cyes.webserver.domain.quiz.service.QuizService;
import com.cyes.webserver.exception.CustomException;
import com.cyes.webserver.exception.CustomExceptionList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;


    
    /*
    라이브 퀴즈쇼 정보 조회 API
    */
    @GetMapping("/live/info")
    private ResponseEntity<QuizInfoResponse> getQuizInfo() {

        return ResponseEntity.status(HttpStatus.OK).body(quizService.searchQuiz());

    }


    /*
    라이브 퀴즈쇼 개설 APi
     */
    @PostMapping
    private ResponseEntity<QuizCreateResponse> createQuiz(@RequestBody QuizCreateRequest quizCreateRequest) {

        // service로 보내는 Dto로 변환
        QuizCreateRequestToService quizCreateRequestToService = quizCreateRequest.create();

        // service 호출
        QuizCreateResponse quizCreateResponse = quizService.createQuiz(quizCreateRequestToService);

        return ResponseEntity.status(HttpStatus.OK).body(quizCreateResponse);
    }

}
