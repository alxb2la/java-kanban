package ru.practicum.kanban;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class ManagersTest {

    @Test
    void shouldGetDefaultInitialized() {
        for (int i = 0; i < 10; i++) {
            TaskManager manager = Managers.getDefault();
            Task task = manager.addTask(new Task("Title1","Description1", TaskStatus.NEW));
            Epic epic = manager.addEpic(new Epic("Title2","Description2"));
            Subtask subtask = manager.addSubtask(new Subtask("Title3","Description3", TaskStatus.IN_PROGRESS, epic.getId()));
            manager.getTask(task.getId());
            manager.getEpic(epic.getId());
            manager.getSubtask(subtask.getId());

            Assertions.assertEquals(1, manager.getListOfTasks().get(0).getId());
            Assertions.assertEquals(2, manager.getListOfEpics().get(0).getId());
            Assertions.assertEquals(3, manager.getListOfSubtasks().get(0).getId());

            Assertions.assertEquals(1, manager.getHistoryManager().getHistory().get(0).getId());
            Assertions.assertEquals(2, manager.getHistoryManager().getHistory().get(1).getId());
            Assertions.assertEquals(3, manager.getHistoryManager().getHistory().get(2).getId());

            Assertions.assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
        }
    }

    @Test
    void getDefaultHistoryInitialized() {
        for (int i = 0; i < 10; i++) {
            HistoryManager manager = Managers.getDefaultHistory();
            Task task = new Task("Title1","Description1", TaskStatus.NEW);
            task.setId(1);
            Epic epic = new Epic("Title2","Description2");
            epic.setId(2);
            Subtask subtask = new Subtask("Title3","Description3", TaskStatus.IN_PROGRESS, epic.getId());
            subtask.setId(3);

            manager.add(task);
            manager.add(epic);
            manager.add(task);
            manager.add(task);
            manager.add(epic);
            manager.add(epic);
            manager.add(subtask);
            manager.add(subtask);
            manager.add(subtask);
            manager.add(subtask);

            Assertions.assertEquals(10, manager.getHistory().size());
            Assertions.assertEquals(1, manager.getHistory().get(0).getId());
            Assertions.assertEquals(3, manager.getHistory().get(9).getId());

            manager.add(task);

            Assertions.assertEquals(10, manager.getHistory().size());
            Assertions.assertEquals(2, manager.getHistory().get(0).getId());
            Assertions.assertEquals(1, manager.getHistory().get(9).getId());
        }
    }
}