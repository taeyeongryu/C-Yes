package com.cyes.webserver.common.controller;

import com.cyes.webserver.common.dto.common.CommonResponse;
import com.cyes.webserver.common.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class ConnectionTest {

    private final ResponseService responseService;

    @GetMapping("/echo")
    public CommonResponse<String> echoMessage(){
        return responseService.getResponse("echo from webserver");
    }

}
