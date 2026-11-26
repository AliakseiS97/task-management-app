package com.aliakseis.taskmanagement.repository;

import com.aliakseis.taskmanagement.model.Project;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Page<Project> findAllByOwnerEmail(String email, Pageable pageable);

    Optional<Project> findByIdAndOwnerEmail(Long id, String email);

    boolean existsByIdAndOwnerEmail(Long id, String email);
}
