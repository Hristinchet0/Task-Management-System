package com.taskmanagementsystem.tms;

import com.taskmanagementsystem.tms.Controller.TaskController;
import com.taskmanagementsystem.tms.Entity.LoginForm;
import com.taskmanagementsystem.tms.Entity.Task;
import com.taskmanagementsystem.tms.Service.TaskService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TmsApplicationTests {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Mock
    private Model mockModel;

    @Mock
    private HttpServletResponse mockResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHome() {
        // Arrange
        List<Task> mockTasks = new ArrayList<>();
        mockTasks.add(new Task());
        when(taskService.getTasks()).thenReturn(mockTasks);

        // Act
        String viewName = taskController.home(mockModel);

        // Assert
        assertEquals("home", viewName);
        verify(mockModel).addAttribute("task", mockTasks);
        verify(taskService).getTasks();
    }

    @Test
    public void testAddTask() {
        // Arrange

        // Act
        String viewName = taskController.addTask();

        // Assert
        assertEquals("add_Task", viewName);
    }

    @Test
    public void testTaskRegister() {
        // Arrange
        Task mockTask = new Task();

        // Act
        String viewName = taskController.taskRegister(mockTask);

        // Assert
        assertEquals("redirect:/home", viewName);
        verify(taskService).addTask(mockTask);
    }

    @Test
    public void testEditTask() {
        // Arrange
        Long taskId = 1L;
        Task mockTask = new Task();
        when(taskService.getTaskById(taskId)).thenReturn(mockTask);

        // Act
        String viewName = taskController.editTask(taskId, mockModel);

        // Assert
        assertEquals("edit", viewName);
        verify(mockModel).addAttribute("task", mockTask);
        verify(taskService).getTaskById(taskId);
    }

    @Test
    public void testUpdateTask() {
        // Arrange
        Task mockTask = new Task();

        // Act
        String viewName = taskController.updateTask(mockTask);

        // Assert
        assertEquals("redirect:/home", viewName);
        verify(taskService).addTask(mockTask);
    }

    @Test
    public void testDeleteTask() {
        // Arrange
        Long taskId = 1L;

        // Act
        String viewName = taskController.deleteTask(taskId);

        // Assert
        assertEquals("redirect:/home", viewName);
        verify(taskService).deleteTaskById(taskId);
    }

    @Test
    public void testExportDataCSV() {
        // Arrange
        String format = "CSV";
        List<Task> mockTasks = new ArrayList<>();
        when(taskService.getTasks()).thenReturn(mockTasks);

        // Act
        taskController.exportData(format, mockResponse);

        // Assert
        verify(taskService).exportToCSV(mockResponse, mockTasks);
    }

    @Test
    public void testExportDataJSON() {
        // Arrange
        String format = "JSON";
        List<Task> mockTasks = new ArrayList<>();
        when(taskService.getTasks()).thenReturn(mockTasks);

        // Act
        taskController.exportData(format, mockResponse);

        // Assert
        verify(taskService).exportToJSON(mockResponse, mockTasks);
    }

    @Test
    public void testExportDataTXT() {
        // Arrange
        String format = "TXT";
        List<Task> mockTasks = new ArrayList<>();
        when(taskService.getTasks()).thenReturn(mockTasks);

        // Act
        taskController.exportData(format, mockResponse);

        // Assert
        verify(taskService).exportToTXT(mockResponse, mockTasks);
    }

    @Test
    public void testExportDataEXCEL() {
        // Arrange
        String format = "EXCEL";
        List<Task> mockTasks = new ArrayList<>();
        when(taskService.getTasks()).thenReturn(mockTasks);

        // Act
        taskController.exportData(format, mockResponse);

        // Assert
        verify(taskService).exportToEXCEL(mockResponse, mockTasks);
    }

    @Test
    public void testDeleteTaskById() {
        taskService.deleteTaskById(1L);
        verify(taskService, times(1)).deleteTaskById(1L);
    }

    @Test
    public void testTaskCreation() {
        Task task = new Task();
        assertNull(task.getId());
        assertNull(task.getTitle());
        assertNull(task.getDescription());
        assertNull(task.getProgress());

        task.setId(1L);
        task.setTitle("Task1");
        task.setDescription("Description1");
        task.setProgress("In Progress");

        assertEquals(1L, task.getId());
        assertEquals("Task1", task.getTitle());
        assertEquals("Description1", task.getDescription());
        assertEquals("In Progress", task.getProgress());
    }

    @Test
    public void testToString() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setDescription("This is Task 1");
        task.setProgress("In Progress");

        String expectedString = "Task{id=1, title='Task 1', description='This is Task 1', isCompleted=In Progress}";
        String actualString = task.toString();

        assertEquals(expectedString, actualString);
    }

    @Test
    void getUsername_returnsCorrectUsername() {
        String expectedUsername = "admin";
        LoginForm loginForm = new LoginForm();
        loginForm.setUsername(expectedUsername);

        String actualUsername = loginForm.getUsername();

        assertEquals(expectedUsername, actualUsername);
    }

    @Test
    void setUsername_setsUsernameCorrectly() {
        String username = "admin";
        LoginForm loginForm = new LoginForm();

        loginForm.setUsername(username);

        assertEquals(username, loginForm.getUsername());
    }

    @Test
    void getPassword_returnsCorrectPassword() {
        String expectedPassword = "admin";
        LoginForm loginForm = new LoginForm();
        loginForm.setPassword(expectedPassword);

        String actualPassword = loginForm.getPassword();

        assertEquals(expectedPassword, actualPassword);
    }

    @Test
    void setPassword_setsPasswordCorrectly() {
        String password = "admin";
        LoginForm loginForm = new LoginForm();

        loginForm.setPassword(password);

        assertEquals(password, loginForm.getPassword());
    }


}
