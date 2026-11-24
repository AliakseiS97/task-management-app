package com.aliakseis.taskmanagement.service;

import com.aliakseis.taskmanagement.dto.user.UserPatchRequestDto;
import com.aliakseis.taskmanagement.dto.user.UserRegistrationRequestDto;
import com.aliakseis.taskmanagement.dto.user.UserResponseDto;
import com.aliakseis.taskmanagement.dto.user.UserUpdateRequestDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);

    UserResponseDto getProfile(String email);

    UserResponseDto updateProfile(String email, UserUpdateRequestDto requestDto);

    UserResponseDto patchProfile(String email, UserPatchRequestDto requestDto);
}
