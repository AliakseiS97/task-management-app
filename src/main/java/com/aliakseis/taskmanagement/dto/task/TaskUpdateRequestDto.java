package com.aliakseis.taskmanagement.dto.task;

import com.aliakseis.taskmanagement.model.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record TaskUpdateRequestDto(
        @NotBlank @Size(max = 255) String name,
        @Size(max = 1000) String description,
        @NotNull Task.Priority priority,
        @NotNull Task.Status status,
        LocalDate dueDate,
        Long assigneeId
) {
}
