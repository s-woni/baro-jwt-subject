package com.example.barojwtsubject.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequest {

    private String username;

    // 필요 시 pattern
    private String password;

    private String nickname;
}
