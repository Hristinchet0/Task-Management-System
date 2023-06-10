package com.taskmanagementsystem.tms.Repository;

import com.taskmanagementsystem.tms.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
