package com.aliakseis.taskmanagement.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record ProjectCreateRequestDto(
        @NotBlank @Size(max = 255) String name,
        @Size(max = 1000) String description,
        LocalDate startDate,
        LocalDate endDate
) {
}
