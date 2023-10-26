package com.cyes.webserver.domain.Answer.controller;

import com.cyes.webserver.domain.Answer.dto.AnswerResponse;
import com.cyes.webserver.domain.Answer.dto.AnswerSaveControllerRequest;
import com.cyes.webserver.domain.Answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answer")
@Slf4j
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("")
    public ResponseEntity<AnswerResponse> save(@RequestBody AnswerSaveControllerRequest answerSaveControllerRequest){
        AnswerResponse answerResponse = answerService.save(answerSaveControllerRequest.toServiceRequest());
        return ResponseEntity.status(HttpStatus.OK).body(answerResponse);
    }

    @GetMapping("")
    public ResponseEntity<List<AnswerResponse>> findAnswerByMemberIdAndQuizId(
            @RequestParam Long memberId,
            @RequestParam Long quizId) {
        List<AnswerResponse> answerByMemberIdAndQuizId = answerService.findAnswerByMemberIdAndQuizId(memberId, quizId);
        return ResponseEntity.status(HttpStatus.OK).body(answerByMemberIdAndQuizId);
    }
}


