package ru.practicum.kanban.managers;

import ru.practicum.kanban.tasks.*;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> tasksViewHistory;

    public InMemoryHistoryManager() {
        tasksViewHistory = new ArrayList<>();
    }


    @Override
    public boolean add(Task task) {
        if (task == null) {
            return false;
        }

        if (tasksViewHistory.size() == 10) {
            tasksViewHistory.remove(0);
        }
        tasksViewHistory.add(task);
        return true;
    }

    @Override
    public List<Task> getHistory() {
        return tasksViewHistory;
    }
}