package com.example.barojwtsubject.domain.user.controller;

import com.example.barojwtsubject.domain.user.dto.LoginRequest;
import com.example.barojwtsubject.domain.user.dto.SignUpRequest;
import com.example.barojwtsubject.domain.user.dto.SignUpResponse;
import com.example.barojwtsubject.domain.user.dto.TokenResponse;
import com.example.barojwtsubject.domain.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "인증", description = "회원가입, 로그인 등 인증 관련 API")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "일반 사용자를 회원가입 시킵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(
                    responseCode = "400",
                    description = "요청 데이터 오류",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "InvalidRequest",
                                    summary = "요청 형식 오류",
                                    value = """
                        {
                          "error": {
                            "code": "INVALID_REQUEST",
                            "message": "입력값이 올바르지 않습니다."
                          }
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 가입된 사용자",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "UserAlreadyExists",
                                    summary = "이미 존재하는 사용자 예시",
                                    value = """
                        {
                          "error": {
                            "code": "USER_ALREADY_EXISTS",
                            "message": "이미 가입된 사용자입니다."
                          }
                        }
                        """
                            )
                    )
            )
    })
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody SignUpRequest request) {

        SignUpResponse response = authService.signup(request);

        return ResponseEntity.ok(response);
    }


    // 테스트 용도 admin 생성용
    @Operation(summary = "관리자 회원가입", description = "관리자 권한을 가진 계정을 생성합니다. (테스트 용도)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "관리자 가입 성공")
    })
    @PostMapping("/signup/admin")
    public ResponseEntity<SignUpResponse> signupAdmin(@RequestBody SignUpRequest request) {

        SignUpResponse response = authService.signupAdmin(request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하여 JWT 토큰을 발급받습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 실패",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "UserNotFound",
                                            summary = "존재하지 않는 사용자",
                                            value = """
                            {
                              "error": {
                                "code": "USER_NOT_FOUND",
                                "message": "존재하지 않는 사용자입니다."
                              }
                            }
                            """
                                    ),
                                    @ExampleObject(
                                            name = "InvalidCredentials",
                                            summary = "비밀번호 불일치",
                                            value = """
                            {
                              "error": {
                                "code": "INVALID_CREDENTIALS",
                                "message": "아이디 또는 비밀번호가 올바르지 않습니다."
                              }
                            }
                            """
                                    )
                            }
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }
}
