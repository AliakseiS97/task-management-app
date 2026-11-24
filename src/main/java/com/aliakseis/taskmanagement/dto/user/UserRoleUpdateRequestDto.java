package com.aliakseis.taskmanagement.dto.user;

import com.aliakseis.taskmanagement.model.Role;
import jakarta.validation.constraints.NotNull;

public record UserRoleUpdateRequestDto(@NotNull Role.RoleName role) {
}
