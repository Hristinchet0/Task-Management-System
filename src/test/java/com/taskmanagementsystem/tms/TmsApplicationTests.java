package com.taskmanagementsystem.tms;

import com.taskmanagementsystem.tms.Entity.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TmsApplicationTests {

    @Test
    void contextLoads() {
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






}
