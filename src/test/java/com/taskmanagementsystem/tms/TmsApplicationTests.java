package com.taskmanagementsystem.tms;

import com.taskmanagementsystem.tms.Entity.Task;
import com.taskmanagementsystem.tms.Repository.TaskRepository;
import com.taskmanagementsystem.tms.Service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class TmsApplicationTests {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTask() {
        Task task = new Task();
        taskService.addTask(task);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testGetTasks() {
        List<Task> taskList = Arrays.asList(new Task(), new Task());
        when(taskRepository.findAll()).thenReturn(taskList);

        List<Task> result = taskService.getTasks();

        verify(taskRepository, times(1)).findAll();
        assertEquals(taskList, result);
    }

    @Test
    public void testGetTaskById() {
        Task task = new Task();
        when(taskRepository.findById(1)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);

        verify(taskRepository, times(1)).findById(1);
        assertEquals(task, result);
    }

    @Test
    public void testDeleteTaskById() {
        taskService.deleteTaskById(1L);
        verify(taskRepository, times(1)).deleteById(1);
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


}
