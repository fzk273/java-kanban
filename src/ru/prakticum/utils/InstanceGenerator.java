package ru.prakticum.utils;


import ru.prakticum.Manager;

public class InstanceGenerator {

    public void createTasks(Manager manager, Integer numberOfTasks) {
        for (int i = 0; i < numberOfTasks; i++) {
            manager.createTask("task"+ i,"descr" + i);
        }
    }

    public void createSubtasks(Manager manager, Integer numberOfSubtasks,Integer epicId){
        for (int i = 0; i < numberOfSubtasks; i++) {
            manager.createSubtask("subtask"+ i,"sub descr" + i, epicId);
        }
    }

    public void createEpics(Manager manager, Integer numberOfEpics){
        for (int i = 0; i < numberOfEpics; i++) {
            manager.createEpic("epic"+ i,"epic descr" + i);
        }
    }
}
