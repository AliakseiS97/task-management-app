package com.aliakseis.taskmanagement.service;

import com.aliakseis.taskmanagement.dto.task.TaskCreateRequestDto;
import com.aliakseis.taskmanagement.dto.task.TaskResponseDto;
import com.aliakseis.taskmanagement.dto.task.TaskUpdateRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskResponseDto create(String email, TaskCreateRequestDto requestDto);

    List<TaskResponseDto> getAllByProject(String email, Long projectId, Pageable pageable);

    TaskResponseDto getById(String email, Long id);

    TaskResponseDto update(String email, Long id, TaskUpdateRequestDto requestDto);

    void delete(String email, Long id);
}
