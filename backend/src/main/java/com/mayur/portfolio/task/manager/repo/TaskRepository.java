package com.mayur.portfolio.task.manager.repo;

import com.mayur.portfolio.task.manager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}