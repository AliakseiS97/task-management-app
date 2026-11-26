package com.aliakseis.taskmanagement.service;

import com.aliakseis.taskmanagement.dto.label.LabelRequestDto;
import com.aliakseis.taskmanagement.dto.label.LabelResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface LabelService {
    LabelResponseDto create(String email, LabelRequestDto requestDto);

    List<LabelResponseDto> getAll(String email, Pageable pageable);

    LabelResponseDto update(String email, Long id, LabelRequestDto requestDto);

    void delete(String email, Long id);
}
