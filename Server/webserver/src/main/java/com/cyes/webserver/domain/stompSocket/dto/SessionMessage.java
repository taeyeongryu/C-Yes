package com.cyes.webserver.domain.stompSocket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}