package ru.prakticum;

import ru.prakticum.utils.InstanceGenerator;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        //TODO do i need to implement a user interface for it??? i hope no :)
        Manager manager = new Manager();
        InstanceGenerator generator = new InstanceGenerator();
        // testing creation
        System.out.println("--------testing creation--------");
        Epic epic = manager.createEpic("epicName", "epicDesc");
        System.out.println(epic);

        Task task = manager.createTask("taskName", "taskDescription");
        manager.createTask("taskName", "taskDescription");
        System.out.println(task);

        SubTask subTask = manager.createSubtask("subName", "subdescription", 0);
        System.out.println(subTask);


        //testing get all
        System.out.println("--------testing get all--------");

        ArrayList<Task> taks = manager.getTasks();
        System.out.println(taks);

        ArrayList<SubTask> subTasks = manager.getSubtasks();
        System.out.println(subTasks);

        ArrayList<Epic> epics = manager.getEpics();
        System.out.println(epics);


        //testing get by id
        System.out.println("--------testing get by id--------");

        Task task1 = manager.getTaskById(1);
        System.out.println(task1);

        SubTask subTask1 = manager.getSubtaskById(3);
        System.out.println(subTask1);

        Epic epic1 = manager.getEpicById(0);
        System.out.println(epic1);

        //testing deletion
        System.out.println("--------testing deletion--------");
        System.out.println(manager.getTasks());
        manager.deleteTasks();
        System.out.println(manager.getTasks());

        System.out.println(manager.getSubtasks());
        manager.deleteSubtasks();
        System.out.println(manager.getSubtasks());

        System.out.println(manager.getEpics());
        manager.deleteEpics();
        System.out.println(manager.getEpics());

        //testing deletion by Id
        System.out.println("--------testing deletion by Id--------");
        generator.createTasks(manager, 5);
        generator.createEpics(manager, 3);
        generator.createSubtasks(manager, 3, 9);
        generator.createSubtasks(manager, 3, 10);
        generator.createSubtasks(manager, 3, 11);
        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());
        System.out.println("\n");

        manager.deleteTaskByID(5);
        manager.deleteTaskByID(6);
        System.out.println(manager.getTasks());
        System.out.println("\n");

        manager.deleteSubtaskById(12);
        manager.deleteSubtaskById(13);
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());
        System.out.println("\n");

        manager.deleteEpicById(9);
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        //testing update
        System.out.println("--------testing update--------");

        System.out.println(manager.getTasks());
        Task task2 = manager.getTaskById(4);
        task2.setDescription("new task descr");
        manager.updateTask(task2);
        System.out.println(manager.getTasks());
        System.out.println("\n");

        System.out.println(manager.getSubtasks());
        SubTask subTask2 = manager.getSubtaskById(16);
        subTask2.setDescription("new subtask descr");
        manager.updateSubtask(subTask2);
        System.out.println(manager.getSubtasks());
        System.out.println("\n");

        System.out.println(manager.getEpics());
        Epic epic2 = manager.getEpicById(10);
        epic2.setDescription("new epic descr");
        manager.updateEpic(epic2);
        System.out.println(manager.getEpics());
        System.out.println("\n");

        System.out.println("Looks like everything is working! See you :)");

    }
}
