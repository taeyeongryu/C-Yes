package com.cyes.webserver.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum CustomExceptionList {

    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E001", "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E002", "서버 에러 발생."),
    REFRESH_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "E003", "토큰이 일치하지 않습니다."),
    ACCESS_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "E004", "토큰이 일치하지 않습니다."),
    MEMBER_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,"E005", "회원 정보를 찾을 수 없습니다."),
    QUIZ_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,"E006","퀴즈 정보를 찾을 수 없습니다."),
    MESSAGE_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,"E007", "메세지가 존재하지 않습니다."),
    CATEGORY_OR_TYPE_MUST_REQUIRED(HttpStatus.BAD_REQUEST,"E007", "카테고리, 타입중 적어도 하나가 필요합니다."),
    ALREADY_SUBMIT(HttpStatus.BAD_REQUEST, "E008", "이미 정답을 제출했습니다."),
    PROBLEM_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND,"E009","PROBLEM을 찾을 수 없습니다."),
    APPLICATION_CONTEXT_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "E010", "스케줄링 중 ApplicationContext를 찾지 못했습니다.");


    private final HttpStatus status;
    private final String code;
    private String message;

    CustomExceptionList(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    CustomExceptionList(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
