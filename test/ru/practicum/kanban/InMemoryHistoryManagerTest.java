package ru.practicum.kanban;

import ru.practicum.kanban.managers.*;
import ru.practicum.kanban.tasks.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

class InMemoryHistoryManagerTest {
    private static InMemoryHistoryManager manager;

    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryHistoryManager();
    }

    @Test
    void shouldAddTaskObjectToTasksViewHistoryAndGetOf() {
        Task task = new Task("Title1","Description1", TaskStatus.NEW);
        Epic epic = new Epic("Title2","Description2");
        Subtask subtask = new Subtask("Title3","Description3", TaskStatus.NEW, 5);
        task.setId(1);
        epic.setId(2);
        subtask.setId(3);
        manager.add(task);
        manager.add(epic);
        manager.add(subtask);
        List<Task> history = manager.getHistory();

        Assertions.assertEquals(task, history.get(0));
        Assertions.assertEquals(epic, history.get(1));
        Assertions.assertEquals(subtask, history.get(2));

        Assertions.assertEquals("Title3", history.get(2).getTitle());
        Assertions.assertEquals("Description3", history.get(2).getDescription());
        Assertions.assertEquals(TaskStatus.NEW, history.get(2).getStatus());
        Assertions.assertEquals(3, history.get(2).getId());
    }

    @Test
    void shouldDeleteTaskObjectFromTasksViewHistory() {
        Task task = new Task("Title1","Description1", TaskStatus.NEW);
        Epic epic = new Epic("Title2","Description2");
        Subtask subtask = new Subtask("Title3","Description3", TaskStatus.NEW, 2);
        task.setId(1);
        epic.setId(2);
        subtask.setId(3);       
        manager.add(task);
        manager.add(epic);
        manager.add(subtask);

        Assertions.assertTrue(manager.remove(1));
        Assertions.assertFalse(manager.remove(1));
        Assertions.assertTrue(manager.remove(2));
        Assertions.assertTrue(manager.remove(3));

        List<Task> history = manager.getHistory();

        Assertions.assertTrue(history.isEmpty());
    }

    @Test
    void shouldNotAddTasksWithSameIdsToTasksViewHistoryList() {
        Task task = new Task("Title1","Description1", TaskStatus.NEW);
        Epic epic = new Epic("Title2","Description2");
        Subtask subtask = new Subtask("Title3","Description3", TaskStatus.NEW, 2);
        task.setId(1);
        epic.setId(2);
        subtask.setId(3);       

        manager.add(task);
        manager.add(epic);
        manager.add(subtask);
        manager.add(epic);
        manager.add(task);
        manager.add(subtask);
        manager.add(task);
        manager.add(task);
        manager.add(epic);
        manager.add(epic);
        manager.add(subtask);
        manager.add(subtask);

        List<Task> history = manager.getHistory();

        Assertions.assertEquals(3, history.size());
    }

    @Test
    void shouldRelocateTaskWithSameIdsToTheEndOfTasksViewHistoryList() {
    	Task task = new Task("Title1","Description1", TaskStatus.NEW);
        Epic epic = new Epic("Title2","Description2");
        Subtask subtask = new Subtask("Title3","Description3", TaskStatus.NEW, 2);
        task.setId(1);
        epic.setId(2);
        subtask.setId(3); 

        manager.add(task);
        manager.add(epic);
        manager.add(subtask);
        manager.add(epic);
        manager.add(task);
        manager.add(subtask);
        List<Task> history = manager.getHistory();

        Assertions.assertEquals(epic, history.get(0));
        Assertions.assertEquals(task, history.get(1));
        Assertions.assertEquals(subtask, history.get(2));
    }
}