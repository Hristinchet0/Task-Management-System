package com.taskmanagementsystem.tms.Controller;

import com.taskmanagementsystem.tms.Entity.Task;
import com.taskmanagementsystem.tms.Service.TaskService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Controller
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String home(Model model) {
        List<Task> tasks = taskService.getTasks();
        model.addAttribute("task", tasks);
        return "index";
    }

    @GetMapping("/addTask")
    public String addTask() {
        return "add_Task";
    }

    @PostMapping("/register")
    public String taskRegister(@ModelAttribute Task t) {
        taskService.addTask(t);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, Model model) {
        Task task = taskService.getTaskById(id);

        model.addAttribute("task", task);
        return "edit";
    }

    @PostMapping("/update")
    public String updateTask(@ModelAttribute Task t) {
        taskService.addTask(t);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return "redirect:/";
    }

    @PostMapping("/export")
    public String exportTasks(@RequestParam("files") String format) {
        logger.info("Exporting tasks in file in format: {}", format);
        List<Task> tasks = taskService.getTasks();

        try {
            if ("CSV".equalsIgnoreCase(format)) {
                taskService.exportToCSV(tasks);
            } else if ("JSON".equalsIgnoreCase(format)) {
                taskService.exportToJSON(tasks);
            } else if ("TXT".equalsIgnoreCase(format)) {
                taskService.exportToTXT(tasks);
            } else if ("EXCEL".equalsIgnoreCase(format)) {
                taskService.exportToEXCEL(tasks);
            } else {
                throw new IllegalArgumentException("Invalid export format: " + format);
            }
        } catch (IOException e) {
            logger.error("Error exporting tasks in file: {}, {}", e.getMessage(), e.getCause());
        }

        return "redirect:/";
    }
}
