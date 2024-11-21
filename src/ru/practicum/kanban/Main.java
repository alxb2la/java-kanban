package ru.practicum.kanban;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        System.out.println();

        TaskManager tManager = new TaskManager();

        Task task1 = new Task("Task1", "Go", TaskStatus.NEW);
        Task task2 = new Task("Task2", "Stay", TaskStatus.NEW);
        Epic epic1 = new Epic("Epic1", "Very important task");
        Epic epic2 = new Epic("Epic2", "Important task");

        tManager.addTask(task1);
        tManager.addTask(task2);
        tManager.addEpic(epic1);
        tManager.addEpic(epic2);

        Subtask subtask1 = new Subtask("Subtask1", "Do epic1 part1", TaskStatus.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("Subtask2", "Do epic1 part2", TaskStatus.NEW, epic1.getId());
        Subtask subtask3 = new Subtask("Subtask3", "Do epic2", TaskStatus.NEW, epic2.getId());

        tManager.addSubtask(subtask1);
        tManager.addSubtask(subtask2);
        tManager.addSubtask(subtask3);

        for (Task task : tManager.getListOfTasks()) {
            System.out.println(task);
        }
        System.out.println();

        for (Epic epic : tManager.getListOfEpics()) {
            System.out.println(epic);
        }
        System.out.println();

        for (Subtask subtask : tManager.getListOfSubtasks()) {
            System.out.println(subtask);
        }
        System.out.println();


        task1.setStatus(TaskStatus.IN_PROGRESS);
        task2.setStatus(TaskStatus.DONE);
        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        subtask2.setStatus(TaskStatus.DONE);
        subtask3.setStatus(TaskStatus.DONE);

        tManager.refreshTask(task1);
        tManager.refreshTask(task2);
        tManager.refreshSubtask(subtask1);
        tManager.refreshSubtask(subtask2);
        tManager.refreshSubtask(subtask3);

        System.out.println("Поменяли статусы задач и подзадач");
        System.out.println();

        for (Task task : tManager.getListOfTasks()) {
            System.out.println(task);
        }
        System.out.println();

        for (Epic epic : tManager.getListOfEpics()) {
            System.out.println(epic);
        }
        System.out.println();

        for (Subtask subtask : tManager.getListOfSubtasks()) {
            System.out.println(subtask);
        }
        System.out.println();

        System.out.println("Удалили задачу и эпик");
        System.out.println();

        tManager.removeTask(task2.getId());
        tManager.removeEpic(epic2.getId());

        for (Task task : tManager.getListOfTasks()) {
            System.out.println(task);
        }
        System.out.println();

        for (Epic epic : tManager.getListOfEpics()) {
            System.out.println(epic);
        }
        System.out.println();

        for (Subtask subtask : tManager.getListOfSubtasks()) {
            System.out.println(subtask);
        }
        System.out.println();

    }
}