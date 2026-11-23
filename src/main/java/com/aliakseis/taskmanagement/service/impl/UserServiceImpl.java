package com.aliakseis.taskmanagement.service.impl;

import com.aliakseis.taskmanagement.dto.user.UserRegistrationRequestDto;
import com.aliakseis.taskmanagement.dto.user.UserResponseDto;
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
}
