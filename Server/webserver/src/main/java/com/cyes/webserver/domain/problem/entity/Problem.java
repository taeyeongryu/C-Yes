package com.cyes.webserver.domain.problem.entity;

public class Problem {
    //pk
    private Long id;
    //내용
    private String content;
    //정답
    private String answer;
    //카테고리(네트워크, 운영체제 등등)
    private ProblemCategory category;
    //문제유형 객관식, 단답형 등등
    private ProblemType type;
}
