package ru.practicum.kanban;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int tasksCounter;

    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Epic> epics;
    private HashMap<Integer, Subtask> subtasks;

    public TaskManager() {
        tasksCounter = 1;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }


    public Task addTask(Task newTask) {
        newTask.setId(tasksCounter);
        tasks.put(tasksCounter, newTask);

        // increase by 1 tasksCounter (task ID)
        tasksCounter += 1;
        return newTask;
    }

    public Epic addEpic(Epic newEpic) {
        newEpic.setId(tasksCounter);
        epics.put(tasksCounter, newEpic);

        // increase by 1 tasksCounter (task ID)
        tasksCounter += 1;
        return newEpic;
    }

    public Subtask addSubtask(Subtask newSubtask) {
        int linkedEpicId = newSubtask.getLinkedEpicId();

        if (epics.containsKey(linkedEpicId)) {
            newSubtask.setId(tasksCounter);
            // increase by 1 tasksCounter (task ID)
            tasksCounter += 1;
            // save subtask to hashmap
            subtasks.put(newSubtask.getId(), newSubtask);

            Epic dirEpic = epics.get(linkedEpicId);
            ArrayList<Integer> linkedSubtasksId = dirEpic.getLinkedSubtasksId();
            // refresh arraylist in Epic object --> add ID of created subtask.
            linkedSubtasksId.add(newSubtask.getId());

            // refresh status in Epic object --> according to technical specification of solution
            dirEpic.setStatus(refreshEpicStatus(linkedSubtasksId));

            return newSubtask;

        } else {
            System.out.println("Epic-задача с данным ID не найдена, Subtask-задача не создана.");
            return null;
        }

    }


    public void refreshTask(Task newTask) {
        // refresh task in hashmap
        tasks.put(newTask.getId(), newTask);
    }

    public void refreshEpic(Epic newEpic) {
        Epic dirEpic = epics.get(newEpic.getId());
        ArrayList<Integer> linkedSubtasksId = dirEpic.getLinkedSubtasksId();

        // Check presence of subtasks
        if (linkedSubtasksId.isEmpty()) {
            // refresh status if no subtasks linked
            dirEpic.setStatus(newEpic.getStatus());
        }
        // refresh title and description fields of Epic
        dirEpic.setTitle(newEpic.getTitle());
        dirEpic.setDescription(newEpic.getDescription());
    }

    public void refreshSubtask(Subtask newSubtask) {
        int linkedEpicId = newSubtask.getLinkedEpicId();
        int newSubtaskId = newSubtask.getId();

        if (epics.containsKey(linkedEpicId)) {
            Epic dirEpic = epics.get(linkedEpicId);
            ArrayList<Integer> linkedSubtasksId = dirEpic.getLinkedSubtasksId();

            // check newSubtask ID --> contains in linkedSubtasksId ArrayList of Epic object or not
            if (linkedSubtasksId.contains(newSubtaskId)) {
                // refresh subtask in hashmap
                subtasks.put(newSubtaskId, newSubtask);
                // refresh status in Epic object --> according to technical specification of solution
                dirEpic.setStatus(refreshEpicStatus(linkedSubtasksId));

            } else {
                System.out.println("Epic-задача не связана с новой Subtask-задачей, Subtask-задача не обновлена.");
            }

        } else {
            System.out.println("Epic-задача по привязанному ID не найдена, Subtask-задача не обновлена.");
        }

    }


    public void removeTask(int taskId) {
        if (tasks.containsKey(taskId)) {
            tasks.remove(taskId);
        } else {
            System.out.println("Задача с данным ID не найдена.");
        }
    }

    public void removeEpic(int epicId) {
        if (epics.containsKey(epicId)) {
            // delete all subtasks witch linked to Epic object
            Epic dirEpic = epics.get(epicId);
            ArrayList<Integer> linkedSubtasksId = dirEpic.getLinkedSubtasksId();
            for (int subtaskId : linkedSubtasksId) {
                subtasks.remove(subtaskId);
            }
            // delete Epic
            epics.remove(epicId);
        } else {
            System.out.println("Задача с данным ID не найдена.");
        }
    }

    public void removeSubtask(int subtaskId) {
        if (subtasks.containsKey(subtaskId)) {
            // get linked Epic and refresh status
            Subtask subtaskToDelete = subtasks.get(subtaskId);
            int linkedEpicTaskId = subtaskToDelete.getLinkedEpicId();
            Epic dirEpic = epics.get(linkedEpicTaskId);
            ArrayList<Integer> linkedSubtasksId = dirEpic.getLinkedSubtasksId();
            // refresh arraylist in Epic object --> remove ID of subtask to delete.
            linkedSubtasksId.remove(subtaskId);
            // refresh status in Epic object --> according to technical specification of solution
            dirEpic.setStatus(refreshEpicStatus(linkedSubtasksId));
            // delete Subtask
            subtasks.remove(subtaskId);
        } else {
            System.out.println("Задача с данным ID не найдена.");
        }
    }


    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void removeAllSubtasks() {
        subtasks.clear();
        for (int epicId : epics.keySet()) {
            Epic dirEpic = epics.get(epicId);
            ArrayList<Integer> linkedSubtasksId = dirEpic.getLinkedSubtasksId();
            linkedSubtasksId.clear();
            dirEpic.setStatus(TaskStatus.NEW);
        }
    }


    public Task getTask(int taskId) {
        return tasks.get(taskId);
    }

    public Epic getEpic(int taskId) {
        return epics.get(taskId);
    }

    public Subtask getSubtask(int taskId) {
        return subtasks.get(taskId);
    }


    public ArrayList<Task> getListOfTasks() {
        ArrayList<Task> tasksToGet = new ArrayList<>();

        for (int taskId : tasks.keySet()) {
            Task dirTask = tasks.get(taskId);
            tasksToGet.add(dirTask);
        }

        return tasksToGet;
    }

    public ArrayList<Epic> getListOfEpics() {
        ArrayList<Epic> epicsToGet = new ArrayList<>();

        for (int epicId : epics.keySet()) {
            Epic dirEpic = epics.get(epicId);
            epicsToGet.add(dirEpic);
        }

        return epicsToGet;
    }

    public ArrayList<Subtask> getListOfSubtasks() {
        ArrayList<Subtask> subtasksToGet = new ArrayList<>();

        for (int subtaskId : subtasks.keySet()) {
            Subtask dirSubtask = subtasks.get(subtaskId);
            subtasksToGet.add(dirSubtask);
        }

        return subtasksToGet;
    }


    public ArrayList<Subtask> getListOfEpicSubtasks(Epic epic) {
        ArrayList<Subtask> subtasksToGet = new ArrayList<>();

        ArrayList<Integer> linkedSubtasksId = epic.getLinkedSubtasksId();
        for (int subtaskId : linkedSubtasksId) {
            Subtask dirSubtask = subtasks.get(subtaskId);
            subtasksToGet.add(dirSubtask);
        }

        return subtasksToGet;
    }


    private TaskStatus refreshEpicStatus(ArrayList<Integer> linkedSubtasksId) {
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