package com.aliakseis.taskmanagement.service;

import com.aliakseis.taskmanagement.dto.project.ProjectCreateRequestDto;
import com.aliakseis.taskmanagement.dto.project.ProjectResponseDto;
import com.aliakseis.taskmanagement.dto.project.ProjectUpdateRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    ProjectResponseDto create(String email, ProjectCreateRequestDto requestDto);

    List<ProjectResponseDto> getAll(String email, Pageable pageable);

    ProjectResponseDto getById(String email, Long id);

    ProjectResponseDto update(String email, Long id, ProjectUpdateRequestDto requestDto);

    void delete(String email, Long id);
}
