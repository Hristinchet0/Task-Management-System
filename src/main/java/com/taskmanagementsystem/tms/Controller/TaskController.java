package com.taskmanagementsystem.tms.Controller;

import com.taskmanagementsystem.tms.Entity.Task;
import com.taskmanagementsystem.tms.Service.TaskServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskServiceImpl taskServiceImpl;

    public TaskController(TaskServiceImpl taskService) {
        this.taskServiceImpl = taskService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<Task> task = taskServiceImpl.getTasks();
        model.addAttribute("task", task);
        return "home";
    }

    @GetMapping("/addTask")
    public String addTask() {
        return "add_Task";
    }

    @PostMapping("/register")
    public String taskRegister(@ModelAttribute Task t) {
        taskServiceImpl.addTask(t);
        return "redirect:/home";
    }

    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, Model model) {
        logger.info("Task is edited");
        Task task = taskServiceImpl.getTaskById(id);

        model.addAttribute("task", task);
        return "edit";
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task t) {
        logger.info("Task is updated");
        taskServiceImpl.addTask(t);

        return "redirect:/home";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        logger.info("Task is deleted");
        taskServiceImpl.deleteTaskById(id);
        return "redirect:/home";
    }

    @GetMapping("/export")
    @ResponseBody
    public void exportData(@RequestParam("files") String format, HttpServletResponse response) {
        logger.info("Exporting tasks in file in format: {}", format);
        List<Task> taskList = taskServiceImpl.getTasks();

        if ("CSV".equalsIgnoreCase(format)) {
            taskServiceImpl.exportToCSV(response, taskList);
        } else if ("JSON".equalsIgnoreCase(format)) {
            taskServiceImpl.exportToJSON(response, taskList);
        } else if ("TXT".equalsIgnoreCase(format)) {
            taskServiceImpl.exportToTXT(response, taskList);
        } else if ("EXCEL".equalsIgnoreCase(format)) {
            taskServiceImpl.exportToEXCEL(response, taskList);
        } else {
            throw new IllegalArgumentException("Invalid export format: " + format);
        }

    }

}
