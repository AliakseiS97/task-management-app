package com.aliakseis.taskmanagement.mapper;

import com.aliakseis.taskmanagement.dto.label.LabelRequestDto;
import com.aliakseis.taskmanagement.dto.label.LabelResponseDto;
import com.aliakseis.taskmanagement.model.Label;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface LabelMapper {
    LabelResponseDto toDto(Label label);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    Label toModel(LabelRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    void updateLabel(LabelRequestDto requestDto, @MappingTarget Label label);
}
