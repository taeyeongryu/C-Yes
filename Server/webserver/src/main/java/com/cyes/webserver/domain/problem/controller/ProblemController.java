package com.cyes.webserver.domain.problem.controller;

import com.cyes.webserver.domain.problem.controller.dto.ProblemSaveControllerRequest;
import com.cyes.webserver.domain.problem.dto.ProblemResponse;
import com.cyes.webserver.domain.problem.dto.ProblemSaveControllerRequest;
import com.cyes.webserver.domain.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProblemController {

    private final ProblemService problemService;
    @PostMapping("")
    public ResponseEntity<ProblemResponse> saveProblem(@RequestBody ProblemSaveControllerRequest problemSaveControllerRequest){
        ProblemResponse problemResponse = problemService.saveProblem(problemSaveControllerRequest.toServiceRequest());
        return ResponseEntity.status(HttpStatus.OK).body(problemResponse);
    }
}
