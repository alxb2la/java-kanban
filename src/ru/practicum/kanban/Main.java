package ru.practicum.kanban;

import ru.practicum.kanban.managers.*;
import ru.practicum.kanban.tasks.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("User scenario of sprint 6 solution");
        System.out.println();

        TaskManager tManager = Managers.getDefault();
        Task task1 = new Task("Task1", "Go", TaskStatus.NEW);
        Task task2 = new Task("Task2", "Stay", TaskStatus.IN_PROGRESS);
        Epic epic1 = new Epic("Epic1", "Very important task");
        Epic epic2 = new Epic("Epic2", "Important task");
        tManager.addTask(task1);
        tManager.addTask(task2);
        tManager.addEpic(epic1);
        tManager.addEpic(epic2);
        Subtask subtask1 = new Subtask("Subtask1", "Do epic1 part1", TaskStatus.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Subtask2", "Do epic1 part2", TaskStatus.IN_PROGRESS, epic1.getId());
        Subtask subtask3 = new Subtask("Subtask3", "Do epic2", TaskStatus.IN_PROGRESS, epic1.getId());
        tManager.addSubtask(subtask1);
        tManager.addSubtask(subtask2);
        tManager.addSubtask(subtask3);

        System.out.println("Getting tasks - circle 1");
        System.out.println();
        
        tManager.getSubtask(subtask2.getId());
        tManager.getSubtask(subtask3.getId());
        tManager.getTask(task2.getId());
        tManager.getEpic(epic1.getId());
        tManager.getTask(task1.getId());
        tManager.getEpic(epic2.getId());
        tManager.getEpic(epic1.getId());
        tManager.getSubtask(subtask1.getId());
        tManager.getSubtask(subtask2.getId());
        tManager.getTask(task1.getId());
        printHistory(tManager);

        System.out.println("Getting tasks - circle 2");
        System.out.println();
        
        tManager.getSubtask(subtask1.getId());
        tManager.getSubtask(subtask2.getId());
        tManager.getTask(task1.getId());
        tManager.getSubtask(subtask2.getId());
        tManager.getSubtask(subtask3.getId());
        tManager.getTask(task2.getId());
        tManager.getEpic(epic1.getId());
        tManager.getTask(task1.getId());
        tManager.getSubtask(subtask3.getId());
        tManager.getSubtask(subtask3.getId());
        printHistory(tManager);
        
        System.out.println("Getting tasks - circle 3");
        System.out.println();
        
        tManager.getSubtask(subtask2.getId());
        tManager.getSubtask(subtask3.getId());
        tManager.getTask(task2.getId());
        tManager.getEpic(epic1.getId());
        tManager.getTask(task1.getId());
        tManager.getSubtask(subtask3.getId());
        tManager.getTask(task1.getId());
        tManager.getEpic(epic2.getId());
        tManager.getEpic(epic2.getId());
        tManager.getTask(task1.getId());
        printHistory(tManager);
        
        System.out.println("Remove task 2");
        System.out.println();
        tManager.removeTask(task2.getId());
        printHistory(tManager);
        
        System.out.println("Remove Epic 1");
        System.out.println();
        tManager.removeEpic(epic1.getId());
        printHistory(tManager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getListOfTasks()) {
            System.out.println(task);
        }

        System.out.println();
        System.out.println("Эпики:");
        for (Task epic : manager.getListOfEpics()) {
            System.out.println(epic);

            for (Task task : manager.getListOfEpicSubtasks(epic.getId())) {
                System.out.println("--> " + task);
            }
        }

        System.out.println();
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getListOfSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println();
        System.out.println("История:");
        for (Task task : manager.getTasksViewHistory()) {
            System.out.println(task);
        }
        System.out.println();
    }
    
    private static void printHistory(TaskManager manager) {
    	System.out.println("History:");
        for (Task task : manager.getTasksViewHistory()) {
            System.out.println(task);
        }
        System.out.println();	
    }
}