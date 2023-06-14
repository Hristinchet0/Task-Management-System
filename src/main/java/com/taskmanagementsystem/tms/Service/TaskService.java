package com.taskmanagementsystem.tms.Service;

import com.taskmanagementsystem.tms.Entity.Task;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface TaskService {

    void addTask(Task task);

    List<Task> getTasks();

    Task getTaskById(Long id);

    void deleteTaskById(Long id);

    void exportToCSV(HttpServletResponse response, List<Task> taskList);

    void exportToJSON(HttpServletResponse response, List<Task> taskList);

    void exportToTXT(HttpServletResponse response, List<Task> taskList);

    void exportToEXCEL(HttpServletResponse response, List<Task> taskList);
}
