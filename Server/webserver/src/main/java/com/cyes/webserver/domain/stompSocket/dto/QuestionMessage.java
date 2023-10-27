package com.cyes.webserver.domain.stompSocket.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestionMessage extends SessionMessage{
    String question;
    List<String> selections;

    public enum QuestionType {
        SINGLE, FOUR, OX
    }
}
