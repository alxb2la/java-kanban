package ru.practicum.kanban.managers;

import ru.practicum.kanban.tasks.*;
import java.util.List;

public interface HistoryManager {
    boolean add(Task task);

    List<Task> getHistory();
}