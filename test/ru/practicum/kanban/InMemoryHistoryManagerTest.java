package ru.practicum.kanban;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;

class InMemoryHistoryManagerTest {
    private static InMemoryHistoryManager manager = new InMemoryHistoryManager();

    @Test
    void shouldAddTaskObjectToTasksViewHistoryListAndGetOf() {
        Task task = new Task("Title1","Description1", TaskStatus.NEW);
        Epic epic = new Epic("Title2","Description2");
        Subtask subtask = new Subtask("Title3","Description3", TaskStatus.NEW, 5);
        manager.add(task);
        manager.add(epic);
        manager.add(subtask);
        ArrayList<Task> history = manager.getHistory();

        Assertions.assertEquals(task, history.get(0));
        Assertions.assertEquals(epic, history.get(1));
        Assertions.assertEquals(subtask, history.get(2));

        Assertions.assertEquals("Title3", history.get(2).getTitle());
        Assertions.assertEquals("Description3", history.get(2).getDescription());
        Assertions.assertEquals(TaskStatus.NEW, history.get(2).getStatus());
        Assertions.assertEquals(0, history.get(2).getId());
    }
}