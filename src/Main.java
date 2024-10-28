import Enums.TaskTypes;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        System.out.println("Поехали!");
        TaskManager taskManager = new TaskManager();
        taskManager.createATask(TaskTypes.TASK,"taskname", "desc");
        taskManager.createATask(TaskTypes.EPIC,"epicname", "desc");
        taskManager.createATask(2, TaskTypes.SUBTASK,"subname", "desc");
        taskManager.createATask(2, TaskTypes.SUBTASK,"subname2", "desc");
        taskManager.createATask(2, TaskTypes.SUBTASK,"subname3", "desc");
        taskManager.createATask(2, TaskTypes.SUBTASK,"subname4", "desc");
        Task OG = taskManager.createATask(2, TaskTypes.SUBTASK,"subname5", "desc");

        HashMap<Integer, Subtask> allSub = taskManager.getAllSubtasksInEpicById(2);
        System.out.println(allSub);
    }
}
