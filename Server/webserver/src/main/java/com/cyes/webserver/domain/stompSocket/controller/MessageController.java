package com.cyes.webserver.domain.stompSocket.controller;

import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.domain.stompSocket.dto.SessionMessage;
import com.cyes.webserver.domain.stompSocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final MemberRepository memberRepository;

    @MessageMapping("/session/message/submit")
    public void submit(
            SessionMessage message
    ) {
        messageService.sendMessage(message);
    }

    @MessageMapping("/session/message/chat")
    public void chat(
            SessionMessage message
    ) {
        messageService.sendMessage(message);
    }
}