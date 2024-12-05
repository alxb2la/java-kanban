package ru.practicum.kanban.managers;

import ru.practicum.kanban.tasks.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int tasksCounter;
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, Subtask> subtasks;
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        tasksCounter = 1;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
    }


    @Override
    public Task addTask(Task newTask) {
        if (newTask.getId() == 0) {
            newTask.setId(tasksCounter);
            tasks.put(tasksCounter, newTask);
            // increase by 1 tasksCounter (task ID)
            tasksCounter += 1;
            return newTask;
        } else {
            return null;
        }
    }

    @Override
    public Epic addEpic(Epic newEpic) {
        if (newEpic.getId() == 0) {
            newEpic.setId(tasksCounter);
            epics.put(tasksCounter, newEpic);
            // increase by 1 tasksCounter (task ID)
            tasksCounter += 1;
            return newEpic;
        } else {
            return null;
        }
    }

    @Override
    public Subtask addSubtask(Subtask newSubtask) {
        if (newSubtask.getId() == 0) {
            int linkedEpicId = newSubtask.getLinkedEpicId();

            if (epics.containsKey(linkedEpicId)) {
                newSubtask.setId(tasksCounter);
                // increase by 1 tasksCounter (task ID)
                tasksCounter += 1;
                // save subtask to hashmap
                subtasks.put(newSubtask.getId(), newSubtask);

                Epic dirEpic = epics.get(linkedEpicId);
                List<Integer> linkedSubtasksId = dirEpic.getLinkedSubtasksId();
                // refresh arraylist in Epic object --> add ID of created subtask.
                linkedSubtasksId.add(newSubtask.getId());
                // refresh status in Epic object --> according to technical specification of solution
                dirEpic.setStatus(refreshEpicStatus(linkedSubtasksId));
                return newSubtask;
            } else {
                //System.out.println("Epic-задача с данным ID не найдена, Subtask-задача не создана.");
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean refreshTask(Task newTask) {
        if (tasks.containsKey(newTask.getId())) {
            // refresh task in hashmap
            tasks.put(newTask.getId(), newTask);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean refreshEpic(Epic newEpic) {
        if (epics.containsKey(newEpic.getId())) {
            Epic dirEpic = epics.get(newEpic.getId());
            // refresh title and description fields of Epic
            dirEpic.setTitle(newEpic.getTitle());
            dirEpic.setDescription(newEpic.getDescription());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean refreshSubtask(Subtask newSubtask) {
        if (subtasks.containsKey(newSubtask.getId())) {
            int linkedEpicId = newSubtask.getLinkedEpicId();
            int newSubtaskId = newSubtask.getId();

            if (epics.containsKey(linkedEpicId)) {
                Epic dirEpic = epics.get(linkedEpicId);
                List<Integer> linkedSubtasksId = dirEpic.getLinkedSubtasksId();
                // check newSubtask ID --> contains in linkedSubtasksId ArrayList of Epic object or not
                if (linkedSubtasksId.contains(newSubtaskId)) {
                    // refresh subtask in hashmap
                    subtasks.put(newSubtaskId, newSubtask);
                    // refresh status in Epic object --> according to technical specification of solution
                    dirEpic.setStatus(refreshEpicStatus(linkedSubtasksId));
                    return true;
                } else {
                    //System.out.println("Epic-задача не связана с новой Subtask-задачей, Subtask-задача не обновлена.");
                    return false;
                }
            } else {
                //System.out.println("Epic-задача по привязанному ID не найдена, Subtask-задача не обновлена.");
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean removeTask(int taskId) {
        if (tasks.containsKey(taskId)) {
            tasks.remove(taskId);
            return true;
        } else {
            //System.out.println("Задача с данным ID не найдена.");
            return false;
        }
    }

    @Override
    public boolean removeEpic(int epicId) {
        if (epics.containsKey(epicId)) {
            // delete all subtasks witch linked to Epic object
            Epic dirEpic = epics.get(epicId);
            List<Integer> linkedSubtasksId = dirEpic.getLinkedSubtasksId();
            for (int subtaskId : linkedSubtasksId) {
                subtasks.remove(subtaskId);
            }
            // delete Epic
            epics.remove(epicId);
            return true;
        } else {
            //System.out.println("Задача с данным ID не найдена.");
            return false;
        }
    }

    @Override
    public boolean removeSubtask(int subtaskId) {
        if (subtasks.containsKey(subtaskId)) {
            // get linked Epic and refresh status
            Subtask subtaskToDelete = subtasks.get(subtaskId);
            int linkedEpicTaskId = subtaskToDelete.getLinkedEpicId();
            Epic dirEpic = epics.get(linkedEpicTaskId);
            List<Integer> linkedSubtasksId = dirEpic.getLinkedSubtasksId();
            // refresh arraylist in Epic object --> remove ID of subtask to delete.
            linkedSubtasksId.remove(linkedSubtasksId.indexOf(subtaskId));
            // refresh status in Epic object --> according to technical specification of solution
            dirEpic.setStatus(refreshEpicStatus(linkedSubtasksId));
            // delete Subtask
            subtasks.remove(subtaskId);
            return true;
        } else {
            //System.out.println("Задача с данным ID не найдена.");
            return false;
        }
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void removeAllSubtasks() {
        subtasks.clear();
        for (int epicId : epics.keySet()) {
            Epic dirEpic = epics.get(epicId);
            List<Integer> linkedSubtasksId = dirEpic.getLinkedSubtasksId();
            linkedSubtasksId.clear();
            dirEpic.setStatus(TaskStatus.NEW);
        }
    }

    @Override
    public Task getTask(int taskId) {
        Task task = tasks.get(taskId);
        // Update view history
        historyManager.add(task);
        // get task by Id
        return task;
    }

    @Override
    public Epic getEpic(int taskId) {
        Epic epic = epics.get(taskId);
        // Update view history
        historyManager.add(epic);
        // get epic by Id
        return epic;
    }

    @Override
    public Subtask getSubtask(int taskId) {
        Subtask subtask = subtasks.get(taskId);
        // Update view history
        historyManager.add(subtask);
        // get subtask by Id
        return subtask;
    }

    @Override
    public List<Task> getListOfTasks() {
        List<Task> tasksToGet = new ArrayList<>();

        for (int taskId : tasks.keySet()) {
            Task dirTask = tasks.get(taskId);
            tasksToGet.add(dirTask);
        }

        return tasksToGet;
    }

    @Override
    public List<Epic> getListOfEpics() {
        List<Epic> epicsToGet = new ArrayList<>();

        for (int epicId : epics.keySet()) {
            Epic dirEpic = epics.get(epicId);
            epicsToGet.add(dirEpic);
        }

        return epicsToGet;
    }

    @Override
    public List<Subtask> getListOfSubtasks() {
        List<Subtask> subtasksToGet = new ArrayList<>();

        for (int subtaskId : subtasks.keySet()) {
            Subtask dirSubtask = subtasks.get(subtaskId);
            subtasksToGet.add(dirSubtask);
        }

        return subtasksToGet;
    }

    @Override
    public List<Subtask> getListOfEpicSubtasks(int taskId) {
        List<Subtask> subtasksToGet = new ArrayList<>();
        Epic epic = epics.get(taskId);

        List<Integer> linkedSubtasksId = epic.getLinkedSubtasksId();
        for (int subtaskId : linkedSubtasksId) {
            Subtask dirSubtask = subtasks.get(subtaskId);
            subtasksToGet.add(dirSubtask);
        }
        return subtasksToGet;
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }


    private TaskStatus refreshEpicStatus(List<Integer> linkedSubtasksId) {
        int newStatusCounter = 0;
        int doneStatusCounter = 0;

        for (int subtaskId : linkedSubtasksId) {
            Subtask dirSubtask = subtasks.get(subtaskId);
            TaskStatus dirStatus = dirSubtask.getStatus();
            if (dirStatus == TaskStatus.NEW) {
                newStatusCounter += 1;
            } else if (dirStatus == TaskStatus.DONE) {
                doneStatusCounter += 1;
            } else {
                return TaskStatus.IN_PROGRESS;
            }
        }

        int size = linkedSubtasksId.size();
        if (size == newStatusCounter) {
            return TaskStatus.NEW;
        } else if (size == doneStatusCounter) {
            return TaskStatus.DONE;
        } else {
            return TaskStatus.IN_PROGRESS;
        }
    }
}