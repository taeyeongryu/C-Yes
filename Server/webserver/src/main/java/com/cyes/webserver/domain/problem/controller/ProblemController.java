package com.cyes.webserver.domain.problem.controller;


import com.cyes.webserver.domain.problem.dto.MultipleChoiceProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.ProblemResponse;
import com.cyes.webserver.domain.problem.dto.ShortAnswerProblemSaveRequest;
import com.cyes.webserver.domain.problem.dto.TrueOrFalseProblemSaveRequest;
import com.cyes.webserver.domain.problem.entity.ProblemCategory;
import com.cyes.webserver.domain.problem.entity.ProblemType;
import com.cyes.webserver.domain.problem.service.ProblemService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/problem")
@Slf4j
public class ProblemController {

    private final ProblemService problemService;

    //문제 등록하는 메서드
    @PostMapping("/multiplechoice")
    public ResponseEntity<ProblemResponse> saveMultipleChoice(@RequestBody MultipleChoiceProblemSaveRequest multipleChoiceProblemSaveRequest){
        System.out.println("multipleChoiceProblemSaveRequest = " + multipleChoiceProblemSaveRequest);
        ProblemResponse problemResponse = problemService.saveMultipleChoice(multipleChoiceProblemSaveRequest);
        return ResponseEntity.status(HttpStatus.OK).body(problemResponse);
    }
    //문제 등록하는 메서드
    @PostMapping("/trueorfalse")
    public ResponseEntity<ProblemResponse> saveTrueOrFalse(@RequestBody TrueOrFalseProblemSaveRequest trueOrFalseProblemSaveRequest){
        ProblemResponse problemResponse = problemService.saveTrueOrFalse(trueOrFalseProblemSaveRequest);
        return ResponseEntity.status(HttpStatus.OK).body(problemResponse);
    }
    //문제 등록하는 메서드
    @PostMapping("/shortanswer")
    public ResponseEntity<ProblemResponse> saveShortAnswer(@RequestBody ShortAnswerProblemSaveRequest shortAnswerProblemSaveRequest){
        ProblemResponse problemResponse = problemService.saveShortAnswer(shortAnswerProblemSaveRequest);
        return ResponseEntity.status(HttpStatus.OK).body(problemResponse);
    }

    @GetMapping("all")
    public ResponseEntity<Page<ProblemResponse>> findAllProblem(Pageable pageable){
        Page<ProblemResponse> allProblem = problemService.findAllProblem(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(allProblem);
    }
    @GetMapping("")
    public ResponseEntity<Page<ProblemResponse>> findProblemByCategoryOrType(
            @RequestParam(name = "problemCategory",required = false) ProblemCategory problemCategory,
            @RequestParam(name = "problemType",required = false) ProblemType problemType,
            Pageable pageable){
        log.info("problemCategory = {}",problemCategory);
        log.info("problemType = {}",problemType);
        log.info("pageable = {}",pageable);
        Page<ProblemResponse> problemByCategoryOrType = problemService.findProblemByCategoryOrType(problemCategory, problemType, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(problemByCategoryOrType);
    }
}


