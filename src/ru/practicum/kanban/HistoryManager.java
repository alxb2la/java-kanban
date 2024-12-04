package ru.practicum.kanban;

import java.util.ArrayList;

public interface HistoryManager {
    boolean add(Task task);
    ArrayList<Task> getHistory();
}
