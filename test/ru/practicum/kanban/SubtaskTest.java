package ru.practicum.kanban;

import ru.practicum.kanban.tasks.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

class SubtaskTest {
    private static Subtask subtask;

    @BeforeEach
    public void beforeEach() {
        subtask = new Subtask("Title","Description", TaskStatus.NEW, 5);
    }

    @Test
    void shouldGetTitleFieldInSubTaskObject() {
        Assertions.assertEquals("Title", subtask.getTitle());
    }

    @Test
    void shouldSetTitleFieldInSubTaskObject() {
        subtask.setTitle("New title");
        Assertions.assertEquals("New title", subtask.getTitle());
    }

    @Test
    void shouldGetDescriptionFieldInSubTaskObject() {
        Assertions.assertEquals("Description", subtask.getDescription());
    }

    @Test
    void shouldSetDescriptionFieldInSubTaskObject() {
        subtask.setDescription("New description");
        Assertions.assertEquals("New description", subtask.getDescription());
    }

    @Test
    void shouldGetStatusFieldInSubTaskObject() {
        Assertions.assertEquals(TaskStatus.NEW, subtask.getStatus());
    }

    @Test
    void shouldSetStatusFieldInSubTaskObject() {
        subtask.setStatus(TaskStatus.DONE);
        Assertions.assertEquals(TaskStatus.DONE, subtask.getStatus());
    }

    @Test
    void shouldGetIdFieldInSubTaskObject() {
        Assertions.assertEquals(0, subtask.getId());
    }

    @Test
    void shouldSetIdFieldInSubTaskObject() {
        subtask.setId(11);
        Assertions.assertEquals(11, subtask.getId());
    }

    @Test
    void shouldGetLinkedEpicIdFieldInSubTaskObject() {
        Assertions.assertEquals(5, subtask.getLinkedEpicId());
    }

    @Test
    void shouldSetLinkedEpicIdFieldInSubTaskObject() {
        subtask.setLinkedEpicId(55);
        Assertions.assertEquals(55, subtask.getLinkedEpicId());
    }

    @Test
    void shouldBeEqualsTwoInstancesOfSubTasksWithSameIds() {
        subtask.setId(3);
        Subtask subTaskWithSameId = new Subtask("Title1","Description1", TaskStatus.NEW, 6);
        subTaskWithSameId.setId(3);
        Assertions.assertTrue(subtask.equals(subTaskWithSameId));
    }
}