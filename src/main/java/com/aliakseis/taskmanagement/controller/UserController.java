package com.aliakseis.taskmanagement.controller;

import com.aliakseis.taskmanagement.dto.user.UserPatchRequestDto;
import com.aliakseis.taskmanagement.dto.user.UserResponseDto;
import com.aliakseis.taskmanagement.dto.user.UserUpdateRequestDto;
import com.aliakseis.taskmanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public UserResponseDto getProfile(Authentication authentication) {
        return userService.getProfile(authentication.getName());
    }

    @PutMapping("/me")
    public UserResponseDto updateProfile(
            Authentication authentication,
            @RequestBody @Valid UserUpdateRequestDto requestDto
    ) {
        return userService.updateProfile(authentication.getName(), requestDto);
    }

    @PatchMapping("/me")
    public UserResponseDto patchProfile(
            Authentication authentication,
            @RequestBody @Valid UserPatchRequestDto requestDto
    ) {
        return userService.patchProfile(authentication.getName(), requestDto);
    }
}
