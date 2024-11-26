package ru.prakticum;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private Integer counter;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, SubTask> subtasks;
    private HashMap<Integer, Epic> epics;

    public Manager() {
        counter = 0;
        tasks = new HashMap();
        subtasks = new HashMap();
        epics = new HashMap();
    }

    public Task createTask(String name, String description) {
        Task task = new Task(counter, name, description);
        tasks.put(counter, task);
        counter++;
        return task;
    }

    public SubTask createSubtask(String name, String description, Integer epicId) {
        SubTask subTask = new SubTask(counter, name, description, epicId);
        subtasks.put(counter, subTask);
        counter++;
        return subTask;
    }

    public Epic createEpic(String name, String description) {
        Epic epic = new Epic(counter, name, description);
        epics.put(counter, epic);
        counter++;
        return epic;
    }

    public ArrayList getTasks() {
        ArrayList tasksArray = new ArrayList<Task>();
        for (Task value : tasks.values()) {
            tasksArray.add(value);
        }
        return tasksArray;
    }

    public ArrayList subTasks() {
        ArrayList subtasksArray = new ArrayList<SubTask>();
        for (SubTask value : subtasks.values()) {
            subtasksArray.add(value);
        }
        return subtasksArray;
    }

    public ArrayList getEpics() {
        ArrayList epicsArray = new ArrayList<Epic>();
        for (Epic value : epics.values()) {
            epicsArray.add(value);
        }
        return epicsArray;
    }

    public boolean deleteTasks() {
        tasks.clear();
        return true;
    }

    public boolean deleteSubtasks(){
        subtasks.clear();
        //TODO implement status check
        return true;
    }

    public boolean deleteEpics(){
        epics.clear();
        //TODO implement deletion of subtasks
        return true;
    }
}
