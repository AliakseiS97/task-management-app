package com.aliakseis.taskmanagement.security;

import com.aliakseis.taskmanagement.dto.user.UserLoginRequestDto;
import com.aliakseis.taskmanagement.dto.user.UserLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        requestDto.email(), requestDto.password()));
        return new UserLoginResponseDto(jwtUtil.generateToken(authentication.getName()));
    }
}
