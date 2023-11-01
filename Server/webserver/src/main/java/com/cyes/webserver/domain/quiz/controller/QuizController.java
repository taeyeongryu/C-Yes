package com.cyes.webserver.domain.quiz.controller;

import com.cyes.webserver.domain.quiz.dto.QuizCreateRequest;
import com.cyes.webserver.domain.quiz.dto.QuizCreateRequestToServiceDto;
import com.cyes.webserver.domain.quiz.dto.QuizCreateResponse;
import com.cyes.webserver.domain.quiz.dto.QuizInfoResponse;
import com.cyes.webserver.domain.quiz.service.QuizService;
import com.cyes.webserver.domain.stompSocket.repository.RedisRepository;
import com.cyes.webserver.domain.stompSocket.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
@Tag(name = "퀴즈", description = "퀴즈에 관한 API")
public class QuizController {

    private final QuizService quizService;
    private final MessageService messageService;
    private final RedisRepository redisRepository;

    /*이거 참고해서 메서드 설명 넣어 주세용~~
     @Operation(summary = "객관식 문제등록", description = "객관식 문제를 등록하는 메서드이다.\n" +
             "question에 문제 내용, answer에 답안 내용\n" +
             "choices에 보기가 String 배열로 4개 들어가면 된다.\n"+
             "problemCategory에는 NETWORK, OS, DB, DATASTRUCTURE, ALGORITHM, DESIGNPATTERN, COMPUTERARCHITECTURE 중 하나가 문자열로 들어가야 한다.")*/
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
    @PostMapping("/create")
    private ResponseEntity<QuizCreateResponse> createQuiz(@RequestBody QuizCreateRequest quizCreateRequest) throws JsonProcessingException {

        // service로 보내는 Dto로 변환
        QuizCreateRequestToServiceDto serviceDto = quizCreateRequest.toServiceDto();

        System.out.println(quizCreateRequest.getQuizStartDate());

        // service 호출
        QuizCreateResponse quizCreateResponse = quizService.createQuiz(serviceDto);

        return ResponseEntity.status(HttpStatus.OK).body(quizCreateResponse);
    }

    @GetMapping("/test")
    private String testRedis() {
        return "1";
    }

}
