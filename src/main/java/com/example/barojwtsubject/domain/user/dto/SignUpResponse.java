package com.example.barojwtsubject.domain.user.dto;

import com.example.barojwtsubject.domain.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpResponse {

    private String username;

    private String nickname;

    private String role;

    public static SignUpResponse from(String username, String nickname, Role role) {

        return new SignUpResponse(username, nickname, role.name());
    }
}
