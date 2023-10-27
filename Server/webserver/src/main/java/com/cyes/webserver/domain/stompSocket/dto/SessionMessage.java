package com.cyes.webserver.domain.stompSocket.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class SessionMessage<T> {
    //세션 ID
    private String sessionId;
    //내용
    private T body;

    private MessageType type;

    /**
     * ENTER : 세션 접속 메세지
     * TALK : 채팅 메시지
     * EXIT : 세션 나가기 메세지
     * RPC : 서버 명령 메세지
     */
    public enum MessageType {
        ENTER, START, QUESTION, SUBMIT, ANSWER, END, RESULT, CHAT
    }

    @Builder
    public SessionMessage(String sessionId, T body, MessageType type) {
        this.sessionId = sessionId;
        this.body = body;
        this.type = type;
    }
}