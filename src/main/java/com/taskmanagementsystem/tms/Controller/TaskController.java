package com.taskmanagementsystem.tms.Controller;

import com.taskmanagementsystem.tms.Entity.Task;
import com.taskmanagementsystem.tms.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Controller
public class TaskController {

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

        List<Task> tasks = taskService.getTasks();

        try {
            if ("CSV".equalsIgnoreCase(format)) {
                exportToCSV(tasks);
            } else if ("JSON".equalsIgnoreCase(format)) {
                exportToJSON(tasks);
            } else if ("TXT".equalsIgnoreCase(format)) {
                exportToTXT(tasks);
            } else if ("EXCEL".equalsIgnoreCase(format)) {
                //
            } else {
                throw new IllegalArgumentException("Invalid export format: " + format);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    private void exportToCSV(List<Task> tasks) throws IOException {
        String csvFile = "tasks.csv";
        try (FileWriter writer = new FileWriter(csvFile)) {

            writer.write("Title,Description,Completed\n");

            for (Task task : tasks) {
                writer.write(String.format("%s,%s,%s\n", task.getTitle(), task.getDescription(), task.getProgress()));
            }
        }
    }

    private void exportToJSON(List<Task> tasks) throws IOException {
        String jsonFile = "tasks.json";
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("[\n");

            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                writer.write(String.format("  {\n    \"title\": \"%s\",\n    \"description\": \"%s\",\n    \"completed\": %s\n  }",
                        task.getTitle(), task.getDescription(), task.getProgress()));
                if (i < tasks.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }

            writer.write("]\n");
        }
    }

    private void exportToTXT(List<Task> tasks) throws IOException {
        String txtFile = "tasks.txt";
        try (FileWriter writer = new FileWriter(txtFile)) {

            for (Task task : tasks) {
                writer.write(String.format("Title: %s\n", task.getTitle()));
                writer.write(String.format("Description: %s\n", task.getDescription()));
                writer.write(String.format("Completed: %s\n", task.getProgress()));
                writer.write("----------------------\n");
            }
        }
    }



}
