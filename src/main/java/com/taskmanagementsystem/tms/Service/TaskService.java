package com.taskmanagementsystem.tms.Service;

import com.taskmanagementsystem.tms.Entity.Task;
import com.taskmanagementsystem.tms.Repository.TaskRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private static final String FILE_PATH = "tasks.xlsx";
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

    public void exportToCSV(List<Task> tasks) throws IOException {
        String csvFile = "tasks.csv";
        try (FileWriter writer = new FileWriter(csvFile)) {

            writer.write("Title,Description,Completed\n");

            for (Task task : tasks) {
                writer.write(String.format("%s,%s,%s\n", task.getTitle(), task.getDescription(), task.getProgress()));
            }
        }
    }

    public void exportToJSON(List<Task> tasks) throws IOException {
        String jsonFile = "tasks.json";
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write("[\n");

            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                writer.write(String.format("  {\n    \"title\": \"%s\",\n    \"description\": \"%s\",\n    \"progress\": %s\n  }",
                        task.getTitle(), task.getDescription(), task.getProgress()));
                if (i < tasks.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }

            writer.write("]\n");
        }
    }

    public void exportToTXT(List<Task> tasks) throws IOException {
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
