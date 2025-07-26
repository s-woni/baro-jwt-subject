package com.example.barojwtsubject.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {

        response.setStatus(HttpStatus.FORBIDDEN.value()); // 403
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        Map<String, Object> error = new HashMap<>();
        error.put("code", "ACCESS_DENIED");
        error.put("message", "접근 권한이 없습니다.");

        Map<String, Object> result = new HashMap<>();
        result.put("error", error);

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
