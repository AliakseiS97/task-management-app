package com.aliakseis.taskmanagement.service.impl;

import com.aliakseis.taskmanagement.dto.task.TaskCreateRequestDto;
import com.aliakseis.taskmanagement.dto.task.TaskResponseDto;
import com.aliakseis.taskmanagement.dto.task.TaskUpdateRequestDto;
import com.aliakseis.taskmanagement.exception.EntityNotFoundException;
import com.aliakseis.taskmanagement.mapper.TaskMapper;
import com.aliakseis.taskmanagement.model.Label;
import com.aliakseis.taskmanagement.model.Project;
import com.aliakseis.taskmanagement.model.Task;
import com.aliakseis.taskmanagement.model.User;
import com.aliakseis.taskmanagement.repository.LabelRepository;
import com.aliakseis.taskmanagement.repository.ProjectRepository;
import com.aliakseis.taskmanagement.repository.TaskRepository;
import com.aliakseis.taskmanagement.repository.UserRepository;
import com.aliakseis.taskmanagement.service.TaskService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final LabelRepository labelRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponseDto create(String email, TaskCreateRequestDto requestDto) {
        Project project = projectRepository.findByIdAndOwnerEmail(requestDto.projectId(), email)
                .orElseThrow(() -> projectNotFound(requestDto.projectId()));
        Task task = taskMapper.toModel(requestDto);
        task.setProject(project);
        task.setAssignee(findAssignee(requestDto.assigneeId()));
        task.setLabels(findLabels(email, requestDto.labelIds()));
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDto> getAllByProject(
            String email,
            Long projectId,
            Pageable pageable
    ) {
        if (!projectRepository.existsByIdAndOwnerEmail(projectId, email)) {
            throw projectNotFound(projectId);
        }
        return taskRepository.findAllByProjectId(projectId, pageable).stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDto getById(String email, Long id) {
        return taskMapper.toDto(findOwnTask(email, id));
    }

    @Override
    @Transactional
    public TaskResponseDto update(String email, Long id, TaskUpdateRequestDto requestDto) {
        Task task = findOwnTask(email, id);
        taskMapper.updateTask(requestDto, task);
        task.setAssignee(findAssignee(requestDto.assigneeId()));
        task.setLabels(findLabels(email, requestDto.labelIds()));
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public void delete(String email, Long id) {
        taskRepository.delete(findOwnTask(email, id));
    }

    private Task findOwnTask(String email, Long id) {
        return taskRepository.findByIdAndProjectOwnerEmail(id, email)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
    }

    private User findAssignee(Long assigneeId) {
        if (assigneeId == null) {
            return null;
        }
        return userRepository.findById(assigneeId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with id: " + assigneeId));
    }

    private Set<Label> findLabels(String email, Set<Long> labelIds) {
        if (labelIds == null || labelIds.isEmpty()) {
            return new HashSet<>();
        }
        Set<Label> labels = new HashSet<>(
                labelRepository.findAllByIdInAndOwnerEmail(labelIds, email));
        if (labels.size() != labelIds.size()) {
            Set<Long> missing = new TreeSet<>(labelIds);
            labels.forEach(label -> missing.remove(label.getId()));
            throw new EntityNotFoundException("Labels not found with ids: " + missing);
        }
        return labels;
    }

    private EntityNotFoundException projectNotFound(Long projectId) {
        return new EntityNotFoundException("Project not found with id: " + projectId);
    }
}
