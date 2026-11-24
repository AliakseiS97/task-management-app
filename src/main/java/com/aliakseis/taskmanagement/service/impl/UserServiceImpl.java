package com.aliakseis.taskmanagement.service.impl;

import com.aliakseis.taskmanagement.dto.user.UserPatchRequestDto;
import com.aliakseis.taskmanagement.dto.user.UserRegistrationRequestDto;
import com.aliakseis.taskmanagement.dto.user.UserResponseDto;
import com.aliakseis.taskmanagement.dto.user.UserUpdateRequestDto;
import com.aliakseis.taskmanagement.exception.ConflictException;
import com.aliakseis.taskmanagement.exception.EntityNotFoundException;
import com.aliakseis.taskmanagement.exception.RegistrationException;
import com.aliakseis.taskmanagement.mapper.UserMapper;
import com.aliakseis.taskmanagement.model.Role;
import com.aliakseis.taskmanagement.model.User;
import com.aliakseis.taskmanagement.repository.RoleRepository;
import com.aliakseis.taskmanagement.repository.UserRepository;
import com.aliakseis.taskmanagement.service.UserService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.email())) {
            throw new RegistrationException("Email is already taken: " + requestDto.email());
        }
        if (userRepository.existsByUsername(requestDto.username())) {
            throw new RegistrationException(
                    "Username is already taken: " + requestDto.username());
        }
        Role userRole = roleRepository.findByName(Role.RoleName.USER)
                .orElseThrow(() -> new IllegalStateException("Role USER is not found"));
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        user.setRoles(Set.of(userRole));
        try {
            return userMapper.toDto(userRepository.saveAndFlush(user));
        } catch (DataIntegrityViolationException e) {
            throw new RegistrationException("Email or username is already taken");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getProfile(String email) {
        return userMapper.toDto(findByEmail(email));
    }

    @Override
    @Transactional
    public UserResponseDto updateProfile(String email, UserUpdateRequestDto requestDto) {
        User user = findByEmail(email);
        checkUsernameAvailable(user, requestDto.username());
        userMapper.updateUser(requestDto, user);
        return saveProfile(user);
    }

    @Override
    @Transactional
    public UserResponseDto patchProfile(String email, UserPatchRequestDto requestDto) {
        User user = findByEmail(email);
        checkUsernameAvailable(user, requestDto.username());
        userMapper.patchUser(requestDto, user);
        return saveProfile(user);
    }

    private User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with email: " + email));
    }

    private void checkUsernameAvailable(User user, String username) {
        if (username != null
                && !username.equals(user.getUsername())
                && userRepository.existsByUsername(username)) {
            throw new ConflictException("Username is already taken: " + username);
        }
    }

    private UserResponseDto saveProfile(User user) {
        try {
            return userMapper.toDto(userRepository.saveAndFlush(user));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Username is already taken");
        }
    }
}
