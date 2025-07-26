package com.example.barojwtsubject.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "회원가입 요청 DTO")
public class SignUpRequest {

    @Schema(description = "사용자 아이디", example = "username", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "비밀번호", example = "1234", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "사용자 닉네임", example = "nickname", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;
}
