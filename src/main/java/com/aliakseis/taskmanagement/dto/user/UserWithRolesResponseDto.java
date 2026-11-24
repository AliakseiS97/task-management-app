package com.aliakseis.taskmanagement.dto.user;

import com.aliakseis.taskmanagement.model.Role;
import java.util.Set;

public record UserWithRolesResponseDto(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        Set<Role.RoleName> roles
) {
}
