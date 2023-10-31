package com.cyes.webserver.domain.stompSocket.controller;

import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.domain.stompSocket.dto.ChatMessage;
import com.cyes.webserver.domain.stompSocket.dto.SessionMessage;
import com.cyes.webserver.domain.stompSocket.dto.SubmitMessage;
import com.cyes.webserver.domain.stompSocket.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final MemberRepository memberRepository;

    /**
     * 유저 입장 핸들링
     */
    @MessageMapping("/session/message/enter")
    public void enter(
            SessionMessage message
    ) {
        messageService.handleEnter(message);
    }

    /**
     * 유저 답안 제출 핸들링
     */
    @MessageMapping("/session/message/submit")
    public void submit(
            SubmitMessage message
    ) throws JsonProcessingException {
        messageService.handleSubmit(message);
    }

    /**
     * 유저 채팅 핸들링
     */
    @MessageMapping("/session/message/chat")
    public void chat(
            ChatMessage message
    ) {
        messageService.handleChat(message);
    }
}