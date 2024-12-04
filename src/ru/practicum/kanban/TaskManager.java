package ru.practicum.kanban;

import java.util.ArrayList;

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

    ArrayList<Task> getListOfTasks();

    ArrayList<Epic> getListOfEpics();

    ArrayList<Subtask> getListOfSubtasks();

    ArrayList<Subtask> getListOfEpicSubtasks(int taskId);

    HistoryManager getHistoryManager();
}