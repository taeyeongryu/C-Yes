package com.cyes.webserver.domain.stompSocket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionMessage {
    //참가한 세션 ID
    private String sessionId;
    //보내는 사람 ID
    private Long senderId;
    //보내는 사람 닉네임
    private String senderNickname;
    //내용
    private String message;

    private MessageType type;

    /**
     * ENTER : 세션 접속 메세지
     * TALK : 채팅 메시지
     * EXIT : 세션 나가기 메세지
     * RPC : 서버 명령 메세지
     */
    public enum MessageType {
        ENTER, TALK, EXIT, RPC
    }

}