package com.aliakseis.taskmanagement.dto.project;

import com.aliakseis.taskmanagement.model.Project;
import java.time.LocalDate;

public record ProjectResponseDto(
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        Project.Status status,
        Long ownerId
) {
}
