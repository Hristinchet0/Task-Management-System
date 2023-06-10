package com.taskmanagementsystem.tms.Controller;

import com.taskmanagementsystem.tms.Entity.Task;
import com.taskmanagementsystem.tms.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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



}
