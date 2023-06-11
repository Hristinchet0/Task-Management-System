package com.taskmanagementsystem.tms.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanagementsystem.tms.Controller.TaskController;
import com.taskmanagementsystem.tms.Entity.Task;
import com.taskmanagementsystem.tms.Repository.TaskRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
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

    public void exportToCSV(HttpServletResponse response, List<Task> taskList) {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=tasks.csv");

        try {
            PrintWriter writer = response.getWriter();
            writer.println("ID, Title, Description, Progress");

            for (Task task : taskList) {
                writer.println(task.getId() + ", " + task.getTitle() + ", " + task.getDescription() + ", " + task.getProgress());
            }

        } catch (IOException e) {
            logger.error("e");
            throw new RuntimeException(e);
        }
    }

    public void exportToJSON(HttpServletResponse response, List<Task> taskList) {
        response.setContentType("application/json");
        response.setHeader("Content-Disposition", "attachment; filename=tasks.json");

        try {
            PrintWriter writer = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(writer, taskList);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportToTXT(HttpServletResponse response, List<Task> taskList) {
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=tasks.txt");

        try {
            PrintWriter writer = response.getWriter();

            for (Task task : taskList) {
                writer.println(
                        task.getId() + "\t" + task.getTitle() + "\t" + task.getDescription() + "\t" + task.getProgress());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportToEXCEL(HttpServletResponse response, List<Task> taskList) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Tasks");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Title");
            headerRow.createCell(2).setCellValue("Description");
            headerRow.createCell(3).setCellValue("Progress");

            int rowNum = 1;
            for (Task task : taskList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(task.getId());
                row.createCell(1).setCellValue(task.getTitle());
                row.createCell(2).setCellValue(task.getDescription());
                row.createCell(3).setCellValue(task.getProgress());
            }

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=tasks.xlsx");

            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
