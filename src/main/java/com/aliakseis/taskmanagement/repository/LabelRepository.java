package com.aliakseis.taskmanagement.repository;

import com.aliakseis.taskmanagement.model.Label;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {
    Page<Label> findAllByOwnerEmail(String email, Pageable pageable);

    Optional<Label> findByIdAndOwnerEmail(Long id, String email);

    boolean existsByOwnerEmailAndName(String email, String name);

    boolean existsByOwnerEmailAndNameAndIdNot(String email, String name, Long id);
}
