package com.cyes.webserver.domain.stompSocket.service.chat;

import com.cyes.webserver.domain.stompSocket.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {


    /**
     * 클라이언트가 보낸 채팅 정보를 처리하는 메서드
     * @param message(ChatMessage)
     */
    public void handleChat(ChatMessage message) {

    }

}