package ru.prakticum.utils;


import ru.prakticum.interfaces.TaskManager;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;

public class InstanceGenerator {

    public void createTasks(TaskManager manager, Integer numberOfTasks) {
        for (int i = 0; i < numberOfTasks; i++) {
            Task task = new Task("task"+ i,"descr" + i);
            manager.createTask(task);
        }
    }

    public void createSubtasks(TaskManager manager, Integer numberOfSubtasks, Integer epicId){
        for (int i = 0; i < numberOfSubtasks; i++) {
            SubTask subTask = new SubTask("subtask"+ i,"sub descr" + i, epicId);
            manager.createSubtask(subTask);
        }
    }

    public void createEpics(TaskManager manager, Integer numberOfEpics){
        for (int i = 0; i < numberOfEpics; i++) {
            Epic epic = new Epic("epic"+ i,"epic descr" + i);
            manager.createEpic(epic);
        }
    }
}
