package com.aliakseis.taskmanagement.controller;

import com.aliakseis.taskmanagement.dto.task.TaskCreateRequestDto;
import com.aliakseis.taskmanagement.dto.task.TaskResponseDto;
import com.aliakseis.taskmanagement.dto.task.TaskUpdateRequestDto;
import com.aliakseis.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDto create(
            Authentication authentication,
            @RequestBody @Valid TaskCreateRequestDto requestDto
    ) {
        return taskService.create(authentication.getName(), requestDto);
    }

    @GetMapping
    public List<TaskResponseDto> getAllByProject(
            Authentication authentication,
            @RequestParam Long projectId,
            @PageableDefault(size = 20, sort = "id") Pageable pageable
    ) {
        return taskService.getAllByProject(authentication.getName(), projectId, pageable);
    }

    @GetMapping("/{id}")
    public TaskResponseDto getById(Authentication authentication, @PathVariable Long id) {
        return taskService.getById(authentication.getName(), id);
    }

    @PutMapping("/{id}")
    public TaskResponseDto update(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody @Valid TaskUpdateRequestDto requestDto
    ) {
        return taskService.update(authentication.getName(), id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Authentication authentication, @PathVariable Long id) {
        taskService.delete(authentication.getName(), id);
    }
}
