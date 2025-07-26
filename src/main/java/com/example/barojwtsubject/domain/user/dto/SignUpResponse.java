package com.example.barojwtsubject.domain.user.dto;

import com.example.barojwtsubject.domain.user.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "회원가입 응답 DTO")
public class SignUpResponse {

    @Schema(description = "사용자 아이디", example = "username")
    private String username;

    @Schema(description = "사용자 닉네임", example = "nickname")
    private String nickname;

    @Schema(description = "사용자 권한", example = "USER or ADMIN")
    private String role;

    public static SignUpResponse from(String username, String nickname, Role role) {

        return new SignUpResponse(username, nickname, role.name());
    }
}
