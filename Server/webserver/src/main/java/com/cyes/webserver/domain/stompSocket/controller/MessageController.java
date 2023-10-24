package com.cyes.webserver.domain.stompSocket.controller;

import com.cyes.webserver.domain.member.repository.MemberRepository;
import com.cyes.webserver.domain.stompSocket.dto.ChatMessage;
import com.cyes.webserver.domain.stompSocket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final ChatService chatService;
    private final MemberRepository memberRepository;

    @MessageMapping("/chat/message")
    public void message(
            ChatMessage message
    ) {
        chatService.sendMessage(message);
    }
}