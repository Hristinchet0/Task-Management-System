package com.taskmanagementsystem.tms.Controller;

import com.taskmanagementsystem.tms.Entity.Task;
import com.taskmanagementsystem.tms.Service.TaskService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @GetMapping("/home")
    public String home(Model model) {
        List<Task> task = taskService.getTasks();
        model.addAttribute("task", task);
        return "home";
    }

    @GetMapping("/addTask")
    public String addTask() {
        return "add_Task";
    }

    @PostMapping("/register")
    public String taskRegister(@ModelAttribute Task t) {
        taskService.addTask(t);
        return "redirect:/home";
    }

    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, Model model) {
        logger.info("Task is edited");
        Task task = taskService.getTaskById(id);

        model.addAttribute("task", task);
        return "edit";
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task t) {
        logger.info("Task is updated");
        taskService.addTask(t);

        return "redirect:/home";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        logger.info("Task is deleted");
        taskService.deleteTaskById(id);
        return "redirect:/home";
    }

    @GetMapping("/export")
    @ResponseBody
    public void exportData(@RequestParam("files") String format, HttpServletResponse response) {
        logger.info("Exporting tasks in file in format: {}", format);
        List<Task> taskList = taskService.getTasks();

        if ("CSV".equalsIgnoreCase(format)) {
            taskService.exportToCSV(response, taskList);
        } else if ("JSON".equalsIgnoreCase(format)) {
            taskService.exportToJSON(response, taskList);
        } else if ("TXT".equalsIgnoreCase(format)) {
            taskService.exportToTXT(response, taskList);
        } else if ("EXCEL".equalsIgnoreCase(format)) {
            taskService.exportToEXCEL(response, taskList);
        } else {
            throw new IllegalArgumentException("Invalid export format: " + format);
        }

    }

}
