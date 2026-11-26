package com.aliakseis.taskmanagement.repository;

import com.aliakseis.taskmanagement.model.Task;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByProjectId(Long projectId, Pageable pageable);

    Optional<Task> findByIdAndProjectOwnerEmail(Long id, String email);
}
