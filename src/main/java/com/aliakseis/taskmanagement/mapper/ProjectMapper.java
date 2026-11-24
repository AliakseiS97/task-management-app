package com.aliakseis.taskmanagement.mapper;

import com.aliakseis.taskmanagement.dto.project.ProjectCreateRequestDto;
import com.aliakseis.taskmanagement.dto.project.ProjectResponseDto;
import com.aliakseis.taskmanagement.dto.project.ProjectUpdateRequestDto;
import com.aliakseis.taskmanagement.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProjectMapper {
    @Mapping(target = "ownerId", source = "owner.id")
    ProjectResponseDto toDto(Project project);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Project toModel(ProjectCreateRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateProject(ProjectUpdateRequestDto requestDto, @MappingTarget Project project);
}
