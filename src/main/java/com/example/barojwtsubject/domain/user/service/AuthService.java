package com.example.barojwtsubject.domain.user.service;

import com.example.barojwtsubject.domain.user.dto.LoginRequest;
import com.example.barojwtsubject.domain.user.dto.SignUpRequest;
import com.example.barojwtsubject.domain.user.dto.SignUpResponse;
import com.example.barojwtsubject.domain.user.dto.TokenResponse;
import com.example.barojwtsubject.domain.user.entity.Role;
import com.example.barojwtsubject.domain.user.entity.User;
import com.example.barojwtsubject.domain.user.repository.UserRepository;
import com.example.barojwtsubject.global.exception.BaseException;
import com.example.barojwtsubject.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.barojwtsubject.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public SignUpResponse signup(SignUpRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BaseException(USER_ALREADY_EXISTS);
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return SignUpResponse.from(user.getUsername(), user.getNickname(), user.getRole());
    }

    public TokenResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BaseException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BaseException(INVALID_CREDENTIALS);
        }

        String token = jwtUtil.createToken(user.getId(), user.getUsername(), user.getRole());
        return new TokenResponse(token);
    }
}
