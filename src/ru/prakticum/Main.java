package ru.prakticum;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        System.out.println("Поехали!");
        Manager manager = new Manager();

        Epic epic = manager.createEpic("epicName", "epicDesc");
        System.out.println(epic);

        Task task = manager.createTask("taskName", "taskDescription");
        manager.createTask("taskName", "taskDescription");
        System.out.println(task);

        SubTask subTask = manager.createSubtask("subName", "subdescription", 0);
        System.out.println(subTask);
        ArrayList tasksArray = manager.getTasks();
        System.out.println(tasksArray);
    }
}
