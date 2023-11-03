package com.cyes.webserver.domain.adminQuiz.controller;

import com.cyes.webserver.domain.Answer.dto.AnswerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adminproblem")
@Slf4j
@Tag(name = "문제들",description = "단답형 문제 생성에 관한 API")
public class adminShortProblem {

    @Operation(summary = "문제 생성", description = "단답형 문제 생성을 도와주는 문제들")
    @GetMapping("/")
    public ResponseEntity<List<AnswerResponse>> findAnswerByMemberIdAndQuizId(
            @RequestParam Long memberId,
            @RequestParam Long quizId) {
        List<AnswerResponse> answerByMemberIdAndQuizId = answerService.findAnswerByMemberIdAndQuizId(memberId, quizId);
        return ResponseEntity.status(HttpStatus.OK).body(answerByMemberIdAndQuizId);
    }
}
