package com.cyes.webserver.domain.stompSocket.controller;

import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.domain.stompSocket.dto.ChatMessage;
import com.cyes.webserver.domain.stompSocket.dto.SessionMessage;
import com.cyes.webserver.domain.stompSocket.dto.SubmitMessage;
import com.cyes.webserver.domain.stompSocket.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final MemberRepository memberRepository;

    @MessageMapping("/session/message/enter")
    public void enter(
            SessionMessage message
    ) {
        messageService.handleEnter(message);
    }

    @MessageMapping("/session/message/submit")
    public void submit(
            SubmitMessage message
    ) {
        messageService.handleSubmit(message);
    }

    @MessageMapping("/session/message/chat")
    public void chat(
            ChatMessage message
    ) {
        messageService.handleChat(message);
    }
}