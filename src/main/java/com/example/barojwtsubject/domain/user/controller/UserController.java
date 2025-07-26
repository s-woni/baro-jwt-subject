package com.example.barojwtsubject.domain.user.controller;

import com.example.barojwtsubject.domain.user.dto.SignUpResponse;
import com.example.barojwtsubject.domain.user.entity.UserDetailsImpl;
import com.example.barojwtsubject.domain.user.service.UserService;
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
public class UserController {

    private final UserService userService;

    @PatchMapping("/users/{userId}/roles")
    public ResponseEntity<?> promoteToAdmin(@PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl currentUser) {

        SignUpResponse response = userService.promoteToAdmin(userId);

        return ResponseEntity.ok(response);
    }
}
