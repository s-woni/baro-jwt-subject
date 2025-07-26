package com.example.barojwtsubject.domain.user.dto;

import lombok.Getter;

@Getter
public class SignUpRequest {

    private String username;

    // 필요 시 pattern
    private String password;

    private String nickname;
}
