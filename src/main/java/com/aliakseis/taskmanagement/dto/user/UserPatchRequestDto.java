package com.aliakseis.taskmanagement.dto.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserPatchRequestDto(
        @Size(min = 3, max = 50) @Pattern(regexp = ".*\\S.*", message = "must not be blank")
        String username,
        @Size(max = 50) @Pattern(regexp = ".*\\S.*", message = "must not be blank")
        String firstName,
        @Size(max = 50) @Pattern(regexp = ".*\\S.*", message = "must not be blank")
        String lastName
) {
}
