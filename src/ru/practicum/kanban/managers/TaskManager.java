package ru.practicum.kanban.managers;

import ru.practicum.kanban.tasks.*;
import java.util.List;

public interface TaskManager {
    Task addTask(Task newTask);

    Epic addEpic(Epic newEpic);

    Subtask addSubtask(Subtask newSubtask);

    boolean refreshTask(Task newTask);

    boolean refreshEpic(Epic newEpic);

    boolean refreshSubtask(Subtask newSubtask);

    boolean removeTask(int taskId);

    boolean removeEpic(int epicId);

    boolean removeSubtask(int subtaskId);

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubtasks();

    Task getTask(int taskId);

    Epic getEpic(int taskId);

    Subtask getSubtask(int taskId);

    List<Task> getListOfTasks();

    List<Epic> getListOfEpics();

    List<Subtask> getListOfSubtasks();

    List<Subtask> getListOfEpicSubtasks(int taskId);

    List<Task> getTasksViewHistory();
}