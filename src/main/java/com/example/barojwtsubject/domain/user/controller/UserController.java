package com.example.barojwtsubject.domain.user.controller;

import com.example.barojwtsubject.domain.user.dto.SignUpResponse;
import com.example.barojwtsubject.domain.user.entity.UserDetailsImpl;
import com.example.barojwtsubject.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "관리자 기능", description = "관리자 권한 관리 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "관리자 권한 부여", description = "일반 유저를 관리자 권한으로 승격시킵니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "관리자 권한 부여 성공",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Success",
                                    summary = "성공 응답 예시",
                                    value = """
                        {
                          "username": "testuser",
                          "nickname": "테스트유저",
                          "role": "ADMIN"
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "접근 권한 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "AccessDenied",
                                    summary = "관리자 권한이 없는 유저가 요청",
                                    value = """
                        {
                          "error": {
                            "code": "ACCESS_DENIED",
                            "message": "관리자 권한이 필요한 요청입니다. 접근 권한이 없습니다."
                          }
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "UserNotFound",
                                    summary = "요청한 유저 ID가 존재하지 않을 경우",
                                    value = """
                        {
                          "error": {
                            "code": "USER_NOT_FOUND",
                            "message": "존재하지 않는 사용자입니다."
                          }
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 관리자 권한을 가진 사용자",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "AlreadyAdmin",
                                    summary = "이미 ADMIN 권한일 경우",
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

    @PatchMapping("/users/{userId}/roles")
    public ResponseEntity<?> promoteToAdmin(@PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl currentUser) {

        SignUpResponse response = userService.promoteToAdmin(userId);

        return ResponseEntity.ok(response);
    }
}
