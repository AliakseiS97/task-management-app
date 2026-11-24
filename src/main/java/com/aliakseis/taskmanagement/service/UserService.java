package com.aliakseis.taskmanagement.service;

import com.aliakseis.taskmanagement.dto.user.UserPatchRequestDto;
import com.aliakseis.taskmanagement.dto.user.UserRegistrationRequestDto;
import com.aliakseis.taskmanagement.dto.user.UserResponseDto;
import com.aliakseis.taskmanagement.dto.user.UserRoleUpdateRequestDto;
import com.aliakseis.taskmanagement.dto.user.UserUpdateRequestDto;
import com.aliakseis.taskmanagement.dto.user.UserWithRolesResponseDto;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);

    UserResponseDto getProfile(String email);

    UserResponseDto updateProfile(String email, UserUpdateRequestDto requestDto);

    UserResponseDto patchProfile(String email, UserPatchRequestDto requestDto);

    UserWithRolesResponseDto updateRole(
            Long id, UserRoleUpdateRequestDto requestDto, String currentUserEmail);
}
