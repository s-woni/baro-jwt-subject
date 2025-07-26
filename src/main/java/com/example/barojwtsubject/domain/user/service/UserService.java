package com.example.barojwtsubject.domain.user.service;

import com.example.barojwtsubject.domain.user.dto.SignUpResponse;
import com.example.barojwtsubject.domain.user.entity.Role;
import com.example.barojwtsubject.domain.user.entity.User;
import com.example.barojwtsubject.domain.user.repository.UserRepository;
import com.example.barojwtsubject.global.exception.BaseException;
import com.example.barojwtsubject.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public SignUpResponse promoteToAdmin(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        user.changeRole(Role.ADMIN);

        return SignUpResponse.from(user.getUsername(), user.getNickname(), user.getRole());
    }
}
