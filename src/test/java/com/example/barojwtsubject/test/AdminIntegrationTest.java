package com.example.barojwtsubject.test;

import com.example.barojwtsubject.domain.user.entity.Role;
import com.example.barojwtsubject.domain.user.entity.User;
import com.example.barojwtsubject.domain.user.repository.UserRepository;
import com.example.barojwtsubject.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String adminToken;
    private String userToken;
    private Long targetUserId;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        // 관리자 계정 생성
        User admin = User.builder()
                .username("admin")
                .nickname("관리자")
                .password(passwordEncoder.encode("1234"))
                .role(Role.ADMIN)
                .build();
        userRepository.save(admin);
        adminToken = jwtUtil.createToken(admin.getId(), admin.getUsername(), admin.getRole());

        // 일반 사용자 생성
        User user = User.builder()
                .username("user")
                .nickname("일반사용자")
                .password(passwordEncoder.encode("1234"))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        userToken = jwtUtil.createToken(user.getId(), user.getUsername(), user.getRole());

        targetUserId = user.getId();
    }

    @Test
    @DisplayName("관리자 권한 부여 성공")
    void 관리자_권한_부여_성공() throws Exception {
        mockMvc.perform(patch("/admin/users/{userId}/roles", targetUserId)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"))
                .andExpect(jsonPath("$.nickname").value("일반사용자"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    @DisplayName("일반 사용자 권한 부여 요청 시 ACCESS_DENIED 발생")
    void 일반_사용자_권한_부여_실패_ACCESS_DENIED() throws Exception {
        mockMvc.perform(patch("/admin/users/{userId}/roles", targetUserId)
                        .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error.code").value("ACCESS_DENIED"))
                .andExpect(jsonPath("$.error.message").exists());
    }

    @Test
    @DisplayName("존재하지 않는 사용자에게 권한 부여 시 USER_NOT_FOUND 발생")
    void 존재하지_않는_사용자_권한_부여_실패() throws Exception {
        Long invalidUserId = 9999L;

        mockMvc.perform(patch("/admin/users/{userId}/roles", invalidUserId)
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.code").value("USER_NOT_FOUND"))
                .andExpect(jsonPath("$.error.message").exists());
    }
}
