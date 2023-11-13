package com.cyes.webserver.domain.stompSocket.controller;

import com.cyes.webserver.domain.stompSocket.dto.ChatMessage;
import com.cyes.webserver.domain.stompSocket.dto.SessionMessage;
import com.cyes.webserver.domain.stompSocket.dto.SubmitMessage;
import com.cyes.webserver.domain.stompSocket.service.chat.ChatService;
import com.cyes.webserver.domain.stompSocket.service.enter.EnterService;
import com.cyes.webserver.domain.stompSocket.service.submit.SubmitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "퀴즈 소켓 통신", description = "퀴즈 소켓 수신 컨트롤러")
public class MessageController {

    private final SubmitService submitService;
    private final ChatService chatService;
    private final EnterService enterService;

    /**
     * 유저 입장 핸들링
     */
    @Operation(summary = "유저 입장", description = "유저 입장을 알려 참여자 수를 증가시킨다.")
    @MessageMapping("/session/message/enter")
    public void enter(SessionMessage message) {
        enterService.handleEnter(message);
    }

    /**
     * 유저 퇴장 핸들링
     */
    @Operation(summary = "유저 퇴장", description = "유저 퇴장을 알려 참여자 수를 감소시킨다.")
    @MessageMapping("/session/message/disconnect")
    public void disconnect(SessionMessage message) {
        enterService.handleDisconnect(message);
    }

    /**
     * 유저 답안 제출 핸들링
     */
    @Operation(summary = "답안 제출", description = "답안을 제출해 저장한다")
    @MessageMapping("/session/message/submit")
    public void submit(SubmitMessage message) throws JsonProcessingException {
        log.info("message = {}", message);
        submitService.handleSubmit(message);
    }
    
    /**
     * 유저 채팅 핸들링
     */
    @Operation(summary = "채팅", description = "채팅을 입력해 다른 사람들에게 전달한다")
    @MessageMapping("/session/message/chat")
    public void chat(ChatMessage message) {
        chatService.handleChat(message);
    }

}