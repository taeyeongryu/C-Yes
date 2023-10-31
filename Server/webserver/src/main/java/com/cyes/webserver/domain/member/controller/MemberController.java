package com.cyes.webserver.domain.member.controller;

import com.cyes.webserver.domain.member.dto.MemberNicknameReq;
import com.cyes.webserver.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/change/nickname")
    public ResponseEntity<String> changeNickname(@RequestBody MemberNicknameReq req) {

        String changedName = memberService.changeNickname(req.getMemberId(), req.getNickname());

        return ResponseEntity.status(HttpStatus.OK).body(changedName);
    }

}
