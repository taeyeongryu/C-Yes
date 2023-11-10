package com.cyes.webserver.domain.problem.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProblemSaveByUserRequest {
    private String question;
    private String[] choices;
    private String answer;
    private String category;
    private String type;
}
