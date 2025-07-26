package com.example.barojwtsubject.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 가입된 사용자입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
