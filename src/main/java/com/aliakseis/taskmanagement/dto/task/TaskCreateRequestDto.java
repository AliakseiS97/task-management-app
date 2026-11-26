package com.aliakseis.taskmanagement.dto.task;

import com.aliakseis.taskmanagement.model.Task;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record TaskCreateRequestDto(
        @NotBlank @Size(max = 255) String name,
        @Size(max = 1000) String description,
        @NotNull Task.Priority priority,
        @FutureOrPresent LocalDate dueDate,
        @NotNull Long projectId,
        Long assigneeId
) {
}
