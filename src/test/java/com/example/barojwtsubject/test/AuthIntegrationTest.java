package com.example.barojwtsubject.test;

import com.example.barojwtsubject.domain.user.dto.SignUpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    static class LoginRequest {
        public String username;
        public String password;

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signup_success() throws Exception {
        SignUpRequest request = new SignUpRequest("test1", "1234", "Tester");

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test1"))
                .andExpect(jsonPath("$.nickname").value("Tester"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    @Test
    @DisplayName("회원가입 실패 - 중복")
    void signup_fail_duplicate_email() throws Exception {
        SignUpRequest request = new SignUpRequest("duplication", "1234", "중복유저");

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error.code").value("USER_ALREADY_EXISTS"))
                .andExpect(jsonPath("$.error.message").value("이미 가입된 사용자입니다."));
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void login_success() throws Exception {
        SignUpRequest signupRequest = new SignUpRequest("loginuser", "1234", "로그인성공유저");

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isOk());

        LoginRequest loginRequest = new LoginRequest("loginuser", "1234");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists());
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 잘못된 비밀번호")
    void login_fail_invalid_credentials() throws Exception {
        SignUpRequest signupRequest = new SignUpRequest("wrongpassuser", "1234", "실패유저");

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isOk());

        LoginRequest loginRequest = new LoginRequest("wrongpassuser", "wrongPassword");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error.code").value("INVALID_CREDENTIALS"))
                .andExpect(jsonPath("$.error.message").value("아이디 또는 비밀번호가 올바르지 않습니다."));
    }
}
