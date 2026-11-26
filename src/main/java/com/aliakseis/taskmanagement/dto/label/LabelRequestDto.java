package com.aliakseis.taskmanagement.dto.label;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LabelRequestDto(
        @NotBlank @Size(max = 50) String name,
        @NotNull
        @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "must be a hex color like #FF0000")
        String color
) {
}
