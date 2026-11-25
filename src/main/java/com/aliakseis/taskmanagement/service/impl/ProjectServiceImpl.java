package com.aliakseis.taskmanagement.service.impl;

import com.aliakseis.taskmanagement.dto.project.ProjectCreateRequestDto;
import com.aliakseis.taskmanagement.dto.project.ProjectResponseDto;
import com.aliakseis.taskmanagement.dto.project.ProjectUpdateRequestDto;
import com.aliakseis.taskmanagement.exception.BadRequestException;
import com.aliakseis.taskmanagement.exception.EntityNotFoundException;
import com.aliakseis.taskmanagement.mapper.ProjectMapper;
import com.aliakseis.taskmanagement.model.Project;
import com.aliakseis.taskmanagement.model.User;
import com.aliakseis.taskmanagement.repository.ProjectRepository;
import com.aliakseis.taskmanagement.repository.UserRepository;
import com.aliakseis.taskmanagement.service.ProjectService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Override
    @Transactional
    public ProjectResponseDto create(String email, ProjectCreateRequestDto requestDto) {
        checkDates(requestDto.startDate(), requestDto.endDate());
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with email: " + email));
        Project project = projectMapper.toModel(requestDto);
        project.setOwner(owner);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDto> getAll(String email, Pageable pageable) {
        return projectRepository.findAllByOwnerEmail(email, pageable).stream()
                .map(projectMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponseDto getById(String email, Long id) {
        return projectMapper.toDto(findOwnProject(email, id));
    }

    @Override
    @Transactional
    public ProjectResponseDto update(
            String email,
            Long id,
            ProjectUpdateRequestDto requestDto
    ) {
        checkDates(requestDto.startDate(), requestDto.endDate());
        Project project = findOwnProject(email, id);
        projectMapper.updateProject(requestDto, project);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    @Transactional
    public void delete(String email, Long id) {
        projectRepository.delete(findOwnProject(email, id));
    }

    private Project findOwnProject(String email, Long id) {
        return projectRepository.findByIdAndOwnerEmail(id, email)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Project not found with id: " + id));
    }

    private void checkDates(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new BadRequestException("End date must not be before start date");
        }
    }
}
