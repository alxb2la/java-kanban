package ru.practicum.kanban;

import ru.practicum.kanban.tasks.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

class TaskTest {
    private static Task task;

    @BeforeEach
    public void beforeEach() {
        task = new Task("Title","Description", TaskStatus.NEW);
    }

    @Test
    void shouldGetTitleFieldInTaskObject() {
        Assertions.assertEquals("Title", task.getTitle());
    }

    @Test
    void shouldSetTitleFieldInTaskObject() {
        task.setTitle("New title");
        Assertions.assertEquals("New title", task.getTitle());
    }

    @Test
    void shouldGetDescriptionFieldInTaskObject() {
        Assertions.assertEquals("Description", task.getDescription());
    }

    @Test
    void shouldSetDescriptionFieldInTaskObject() {
        task.setDescription("New description");
        Assertions.assertEquals("New description", task.getDescription());
    }

    @Test
    void shouldGetStatusFieldInTaskObject() {
        Assertions.assertEquals(TaskStatus.NEW, task.getStatus());
    }

    @Test
    void shouldSetStatusFieldInTaskObject() {
        task.setStatus(TaskStatus.DONE);
        Assertions.assertEquals(TaskStatus.DONE, task.getStatus());
    }


    @Test
    void shouldGetIdFieldInTaskObject() {
        Assertions.assertEquals(0, task.getId());
    }

    @Test
    void shouldSetIdFieldInTaskObject() {
        task.setId(11);
        Assertions.assertEquals(11, task.getId());
    }

    @Test
    void shouldBeEqualsTwoInstancesOfTasksWithSameIds() {
        task.setId(11);
        Task taskWithSameId = new Task("Title1","Description1", TaskStatus.NEW);
        taskWithSameId.setId(11);
        Assertions.assertTrue(task.equals(taskWithSameId));
    }
}