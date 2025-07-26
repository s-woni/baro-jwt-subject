package com.example.barojwtsubject.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Map<String, Object>> handleBaseException(BaseException e) {

        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(Map.of(
                        "error", Map.of(
                                "code", e.getErrorCode().name(),
                                "message", e.getErrorCode().getMessage()
                        )
                ));
    }
}
