package ru.practicum.kanban;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

class EpicTest {
    private static Epic epic;

    @BeforeEach
    public void beforeEach() {
        epic = new Epic("Title","Description");
    }

    @Test
    void shouldGetTitleFieldInEpicObject() {
        Assertions.assertEquals("Title", epic.getTitle());
    }

    @Test
    void shouldSetTitleFieldInEpicObject() {
        epic.setTitle("New title");
        Assertions.assertEquals("New title", epic.getTitle());
    }

    @Test
    void shouldGetDescriptionFieldInEpicObject() {
        Assertions.assertEquals("Description", epic.getDescription());
    }

    @Test
    void shouldSetDescriptionFieldInEpicObject() {
        epic.setDescription("New description");
        Assertions.assertEquals("New description", epic.getDescription());
    }

    @Test
    void shouldGetStatusFieldInEpicObject() {
        Assertions.assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    void shouldSetStatusFieldInEpicObject() {
        epic.setStatus(TaskStatus.DONE);
        Assertions.assertEquals(TaskStatus.DONE, epic.getStatus());
    }


    @Test
    void shouldGetIdFieldInEpicObject() {
        Assertions.assertEquals(0, epic.getId());
    }

    @Test
    void shouldSetIdFieldInEpicObject() {
        epic.setId(11);
        Assertions.assertEquals(11, epic.getId());
    }

    @Test
    void shouldGetLinkedSubtasksIdFieldInEpicObject() {
        Assertions.assertEquals(List.of(), epic.getLinkedSubtasksId());
    }

    @Test
    void shouldSetLinkedSubtasksIdFieldInEpicObject() {
        ArrayList<Integer> subtasksId = new ArrayList<>(List.of(7, 11, 13));
        epic.setLinkedSubtasksId(subtasksId);
        Assertions.assertNotNull(epic.getLinkedSubtasksId());
    }

    @Test
    void shouldBeEqualsTwoInstancesOfEpicsWithSameIds() {
        epic.setId(11);
        Epic epicWithSameId = new Epic("Title1","Description1");
        epicWithSameId.setId(11);
        Assertions.assertTrue(epic.equals(epicWithSameId));
    }
}