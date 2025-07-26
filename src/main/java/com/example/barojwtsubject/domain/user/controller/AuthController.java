package com.example.barojwtsubject.domain.user.controller;

import com.example.barojwtsubject.domain.user.dto.LoginRequest;
import com.example.barojwtsubject.domain.user.dto.SignUpRequest;
import com.example.barojwtsubject.domain.user.dto.SignUpResponse;
import com.example.barojwtsubject.domain.user.dto.TokenResponse;
import com.example.barojwtsubject.domain.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody SignUpRequest request) {

        SignUpResponse response = authService.signup(request);

        return ResponseEntity.ok(response);
    }

    // 테스트 용도 admin 생성용
    @PostMapping("/signup/admin")
    public ResponseEntity<SignUpResponse> signupAdmin(@RequestBody SignUpRequest request) {

        SignUpResponse response = authService.signupAdmin(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }
}
