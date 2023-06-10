package com.taskmanagementsystem.tms.Service;

import com.taskmanagementsystem.tms.Entity.Task;
import com.taskmanagementsystem.tms.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public void addTask(Task task) {
        taskRepository.save(task);
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(Math.toIntExact(id));
        return task.orElse(null);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(Math.toIntExact(id));
    }
}
