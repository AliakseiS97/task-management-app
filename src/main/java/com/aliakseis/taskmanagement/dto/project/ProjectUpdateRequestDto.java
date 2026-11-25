package com.aliakseis.taskmanagement.dto.project;

import com.aliakseis.taskmanagement.model.Project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record ProjectUpdateRequestDto(
        @NotBlank @Size(max = 255) String name,
        @Size(max = 1000) String description,
        LocalDate startDate,
        LocalDate endDate,
        @NotNull Project.Status status
) {
}
