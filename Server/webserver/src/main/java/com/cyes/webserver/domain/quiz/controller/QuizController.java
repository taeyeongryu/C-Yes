package com.cyes.webserver.domain.quiz.controller;

import com.cyes.webserver.domain.quiz.dto.QuizCreateRequest;
import com.cyes.webserver.domain.quiz.dto.QuizCreateRequestToService;
import com.cyes.webserver.domain.quiz.dto.QuizCreateResponse;
import com.cyes.webserver.domain.quiz.dto.QuizInfoResponse;
import com.cyes.webserver.domain.quiz.service.QuizService;
import com.cyes.webserver.domain.stompSocket.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final MessageService messageService;


    
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
    private ResponseEntity<QuizCreateResponse> createQuiz(@RequestBody QuizCreateRequest quizCreateRequest) throws JsonProcessingException {

        // service로 보내는 Dto로 변환
        QuizCreateRequestToService quizCreateRequestToService = quizCreateRequest.create();

        // service 호출
        QuizCreateResponse quizCreateResponse = quizService.createQuiz(quizCreateRequestToService);

        return ResponseEntity.status(HttpStatus.OK).body(quizCreateResponse);
    }

}
