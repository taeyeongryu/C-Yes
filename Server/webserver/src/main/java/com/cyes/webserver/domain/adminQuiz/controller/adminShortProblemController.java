package com.cyes.webserver.domain.adminQuiz.controller;


import com.cyes.webserver.domain.adminQuiz.service.OpenAIPostRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adminproblem")
@Slf4j
@Tag(name = "문제들",description = "단답형 문제 생성에 관한 API")
public class adminShortProblemController {

    private final OpenAIPostRequestService openAIPostRequestService;


    @Operation(summary = "문제 생성", description = "단답형 문제 생성을 도와주는 문제들")
    @GetMapping("/create/{word}")
    public List<String> createShortProblem(@PathVariable("word") String word) throws JsonProcessingException {
        List<String> wordlist = openAIPostRequestService.sendPostword(word);

        String[] newStr = wordlist.get(0).split("\n");
        for (int i=0;i<newStr.length;i++){
            System.out.println("didi : " + newStr[i]);
            openAIPostRequestService.makeShortProblem(newStr[i]);
        }
        return wordlist;

    }
}
