package com.aliakseis.taskmanagement.mapper;

import com.aliakseis.taskmanagement.dto.task.TaskCreateRequestDto;
import com.aliakseis.taskmanagement.dto.task.TaskResponseDto;
import com.aliakseis.taskmanagement.dto.task.TaskUpdateRequestDto;
import com.aliakseis.taskmanagement.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TaskMapper {
    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "assigneeId", source = "assignee.id")
    TaskResponseDto toDto(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Task toModel(TaskCreateRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateTask(TaskUpdateRequestDto requestDto, @MappingTarget Task task);
}
