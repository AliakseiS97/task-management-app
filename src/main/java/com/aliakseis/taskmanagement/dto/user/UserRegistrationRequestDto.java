package com.aliakseis.taskmanagement.dto.user;

import com.aliakseis.taskmanagement.validation.PasswordsMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@PasswordsMatch
public record UserRegistrationRequestDto(
        @NotBlank @Size(min = 3, max = 50) String username,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8, max = 100) String password,
        @NotBlank String repeatPassword,
        @NotBlank @Size(max = 50) String firstName,
        @NotBlank @Size(max = 50) String lastName
) {
}
