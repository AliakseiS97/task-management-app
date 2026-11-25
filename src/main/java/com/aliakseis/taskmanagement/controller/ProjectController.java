package com.aliakseis.taskmanagement.controller;

import com.aliakseis.taskmanagement.dto.project.ProjectCreateRequestDto;
import com.aliakseis.taskmanagement.dto.project.ProjectResponseDto;
import com.aliakseis.taskmanagement.dto.project.ProjectUpdateRequestDto;
import com.aliakseis.taskmanagement.service.ProjectService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponseDto create(
            Authentication authentication,
            @RequestBody @Valid ProjectCreateRequestDto requestDto
    ) {
        return projectService.create(authentication.getName(), requestDto);
    }

    @GetMapping
    public List<ProjectResponseDto> getAll(
            Authentication authentication,
            @PageableDefault(size = 20, sort = "id") Pageable pageable
    ) {
        return projectService.getAll(authentication.getName(), pageable);
    }

    @GetMapping("/{id}")
    public ProjectResponseDto getById(Authentication authentication, @PathVariable Long id) {
        return projectService.getById(authentication.getName(), id);
    }

    @PutMapping("/{id}")
    public ProjectResponseDto update(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody @Valid ProjectUpdateRequestDto requestDto
    ) {
        return projectService.update(authentication.getName(), id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Authentication authentication, @PathVariable Long id) {
        projectService.delete(authentication.getName(), id);
    }
}
