package com.aliakseis.taskmanagement.dto.task;

import com.aliakseis.taskmanagement.model.Task;
import java.time.LocalDate;
import java.util.Set;

public record TaskResponseDto(
        Long id,
        String name,
        String description,
        Task.Priority priority,
        Task.Status status,
        LocalDate dueDate,
        Long projectId,
        Long assigneeId,
        Set<Long> labelIds
) {
}
