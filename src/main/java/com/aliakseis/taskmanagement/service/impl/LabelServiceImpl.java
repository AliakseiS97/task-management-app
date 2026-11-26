package com.aliakseis.taskmanagement.service.impl;

import com.aliakseis.taskmanagement.dto.label.LabelRequestDto;
import com.aliakseis.taskmanagement.dto.label.LabelResponseDto;
import com.aliakseis.taskmanagement.exception.ConflictException;
import com.aliakseis.taskmanagement.exception.EntityNotFoundException;
import com.aliakseis.taskmanagement.mapper.LabelMapper;
import com.aliakseis.taskmanagement.model.Label;
import com.aliakseis.taskmanagement.model.User;
import com.aliakseis.taskmanagement.repository.LabelRepository;
import com.aliakseis.taskmanagement.repository.UserRepository;
import com.aliakseis.taskmanagement.service.LabelService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {
    private final LabelRepository labelRepository;
    private final UserRepository userRepository;
    private final LabelMapper labelMapper;

    @Override
    @Transactional
    public LabelResponseDto create(String email, LabelRequestDto requestDto) {
        if (labelRepository.existsByOwnerEmailAndName(email, requestDto.name())) {
            throw labelExists(requestDto.name());
        }
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with email: " + email));
        Label label = labelMapper.toModel(requestDto);
        label.setOwner(owner);
        return saveLabel(label);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LabelResponseDto> getAll(String email, Pageable pageable) {
        return labelRepository.findAllByOwnerEmail(email, pageable).stream()
                .map(labelMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public LabelResponseDto update(String email, Long id, LabelRequestDto requestDto) {
        Label label = findOwnLabel(email, id);
        if (labelRepository.existsByOwnerEmailAndNameAndIdNot(email, requestDto.name(), id)) {
            throw labelExists(requestDto.name());
        }
        labelMapper.updateLabel(requestDto, label);
        return saveLabel(label);
    }

    @Override
    @Transactional
    public void delete(String email, Long id) {
        labelRepository.delete(findOwnLabel(email, id));
    }

    private Label findOwnLabel(String email, Long id) {
        return labelRepository.findByIdAndOwnerEmail(id, email)
                .orElseThrow(() -> new EntityNotFoundException("Label not found with id: " + id));
    }

    private LabelResponseDto saveLabel(Label label) {
        try {
            return labelMapper.toDto(labelRepository.saveAndFlush(label));
        } catch (DataIntegrityViolationException e) {
            throw labelExists(label.getName());
        }
    }

    private ConflictException labelExists(String name) {
        return new ConflictException("Label already exists: " + name);
    }
}
