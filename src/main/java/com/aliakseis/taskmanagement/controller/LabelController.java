package com.aliakseis.taskmanagement.controller;

import com.aliakseis.taskmanagement.dto.label.LabelRequestDto;
import com.aliakseis.taskmanagement.dto.label.LabelResponseDto;
import com.aliakseis.taskmanagement.service.LabelService;
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
@RequestMapping("/api/labels")
@RequiredArgsConstructor
public class LabelController {
    private final LabelService labelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelResponseDto create(
            Authentication authentication,
            @RequestBody @Valid LabelRequestDto requestDto
    ) {
        return labelService.create(authentication.getName(), requestDto);
    }

    @GetMapping
    public List<LabelResponseDto> getAll(
            Authentication authentication,
            @PageableDefault(size = 50, sort = "name") Pageable pageable
    ) {
        return labelService.getAll(authentication.getName(), pageable);
    }

    @PutMapping("/{id}")
    public LabelResponseDto update(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody @Valid LabelRequestDto requestDto
    ) {
        return labelService.update(authentication.getName(), id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(Authentication authentication, @PathVariable Long id) {
        labelService.delete(authentication.getName(), id);
    }
}
