package ru.practicum.kanban;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> tasksViewHistory;

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
    public ArrayList<Task> getHistory() {
        return tasksViewHistory;
    }
}
