package ru.prakticum;

import ru.prakticum.interfaces.TaskManager;
import ru.prakticum.managers.FileBackedTaskManager;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;
import ru.prakticum.utils.InstanceGenerator;
import ru.prakticum.utils.Managers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

//        LocalDateTime nowTime = LocalDateTime.now();
//        Duration oneHour = Duration.ofHours(1);

//        inMemoryTaskManagerUserStory(nowTime, oneHour);
//        fileBackedTaskManagerUserStory(nowTime, oneHour);
//        try {
//            fileBackedHttpServerUserStory();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        try {
            inMemoryHttpServerUserStory();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void inMemoryTaskManagerUserStory(LocalDateTime nowTime, Duration oneHour) {
        TaskManager manager = Managers.getDefault();
        InstanceGenerator generator = new InstanceGenerator();


        // testing creation
        System.out.println("--------testing creation--------");
        Epic epic = new Epic("epicName", "epicDesc");

        manager.createEpic(epic);
        System.out.println(epic);

        Task task = new Task("taskName", "taskDescription");
        manager.createTask(task);
        System.out.println(task);

        SubTask subTask = new SubTask("subName", "subdescription", 0);
        manager.createSubtask(subTask);
        System.out.println(subTask);


        //testing get all
        System.out.println("--------testing get all--------");

        ArrayList<Task> taks = manager.getTasks();
        System.out.println(taks);

        ArrayList<SubTask> subTasks = manager.getSubtasks();
        System.out.println(subTasks);

        ArrayList<Epic> epics = manager.getEpics();
        System.out.println(epics);

        System.out.println(manager.getHistory());
        //testing get by id
        System.out.println("--------testing get by id--------");

        Task task1 = manager.getTaskById(1);
        System.out.println(task1);

        SubTask subTask1 = manager.getSubtaskById(2);
        System.out.println(subTask1);

        Epic epic1 = manager.getEpicById(0);
        System.out.println(epic1);

        System.out.println(manager.getHistory());
        manager.getTaskById(1);
        System.out.println(manager.getHistory());

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


//        testing deletion by Id
        System.out.println("--------testing deletion by Id--------");
        generator.createTasks(manager, 5);
        generator.createEpics(manager, 3);
        generator.createSubtasks(manager, 3, 9);
        generator.createSubtasks(manager, 3, 10);
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


//        testing update
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
        System.out.println(manager.getHistory());

    }

    public static void fileBackedTaskManagerUserStory(LocalDateTime nowTime, Duration oneHour) {
        File backupFile = new File(Paths.get("src/ru/prakticum/resources/save_example.csv").toString());

        FileBackedTaskManager fbManager = FileBackedTaskManager.loadFromFile(backupFile);
        System.out.println("---------------------------------------");
        System.out.println(fbManager.getSubtasks());
        System.out.println(fbManager.getEpics());
        System.out.println(fbManager.getTasks());
        System.out.println(fbManager.getEpicById(2).getStartTime());
        System.out.println(fbManager.getEpicById(2).getEndTime());
        System.out.println(fbManager.getEpicById(2).getDuration());
        System.out.println(fbManager.getPrioritizedTasks());

//        Task fbTask = new Task("FB name2", "FB desc2");
//        Task fbTask2 = new Task("FB name2", "FB desc2");
//        Task fbTask3 = new Task("FB name2", "FB desc2");
//        fbManager.createTask(fbTask);
//        fbManager.createTask(fbTask2);
//        fbManager.createTask(fbTask3);
//        Epic epic3 = new Epic("FB epic", "FB desc");
//        Epic epic4 = new Epic("FB epic", "FB desc");
//        Epic epic5 = new Epic("FB epic", "FB desc");
//        fbManager.createEpic(epic3);
//        fbManager.createEpic(epic4);
//        fbManager.createEpic(epic5);
//        System.out.println(fbManager.getEpics());
//        SubTask fbSub = new SubTask("FB sub", "FB desc", 2);
//        SubTask fbSub2 = new SubTask("FB sub", "FB desc", 2);
//        SubTask fbSub3 = new SubTask("FB sub", "FB desc", 2);
//        fbManager.createSubtask(fbSub);
//        fbManager.createSubtask(fbSub2);
//        fbManager.createSubtask(fbSub3);
//        System.out.println(fbManager.getSubtasks());
    }

    public static void fileBackedHttpServerUserStory() throws IOException {
        TaskManager taskManager = Managers.getFileBackedTaskManager();
        HttpTaskServer httpTaskServer = new HttpTaskServer(taskManager);
        httpTaskServer.start();
    }


    public static void inMemoryHttpServerUserStory() throws IOException {
        TaskManager taskManager = Managers.getDefault();
        HttpTaskServer httpTaskServer = new HttpTaskServer(taskManager);
        httpTaskServer.start();
    }
}
