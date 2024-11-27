package ru.prakticum;

import ru.prakticum.enums.Status;

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

    public ArrayList getSubtasks() {
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

    public Task getTaskById(Integer id) {
        return tasks.get(id);
    }

    public SubTask getSubtaskById(Integer id) {
        return subtasks.get(id);
    }

    public Epic getEpicById(Integer id) {
        return epics.get(id);
    }

    public boolean deleteTasks() {
        tasks.clear();
        return true;
    }

    public boolean deleteSubtasks() {
        subtasks.clear();
        //TODO implement status check
        return true;
    }

    public boolean deleteEpics() {
        epics.clear();
        //TODO ???implement deletion of subtasks???
        return true;
    }

    public boolean deleteTaskByID(Integer id) {
        tasks.remove(id);
        return true;
    }

    public boolean deleteSubtaskById(Integer id) {
        subtasks.remove(id);
        return true;
    }

    public boolean deleteEpicById(Integer id) {
        ArrayList<SubTask> epicSubtasks = getEpicSubtasks(getEpicById(id));
        for (SubTask subtask : epicSubtasks) {
            deleteSubtaskById(subtask.getId());
        }
        epics.remove(id);
        return true;
    }

    public boolean updateTask(Task task) {
        tasks.put(task.getId(), task);
        return true;
    }

    public boolean updateSubtask(SubTask subtask) {
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(getEpicById(subtask.getEpicId()));
        return true;
    }

    public boolean updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        return true;
    }

    public ArrayList<SubTask> getEpicSubtasks(Epic epic) {
        ArrayList<SubTask> epicsSubtasks = new ArrayList<>();
        ArrayList<Integer> epicSubtasksIds = epic.getSubtaskIds();
        for (Integer subtaskId : epicSubtasksIds) {
            epicsSubtasks.add(getSubtaskById(subtaskId));
        }
        return epicsSubtasks;
    }

    private void updateEpicStatus(Epic epic) {
        ArrayList<SubTask> epicsSubtasks = getEpicSubtasks(epic);
        if (epicsSubtasks.isEmpty()) {
            epic.setStatus(Status.NEW);
        } else {
            boolean allTasksAreNew = true;
            boolean allTasksAreDone = true;
            for (SubTask subTask : epicsSubtasks) {
                if (!subTask.getStatus().equals(Status.NEW)){
                    allTasksAreNew = false;
                }
                if (!subTask.getStatus().equals(Status.DONE)){
                    allTasksAreDone = false;
                }
            }
            if (allTasksAreDone){
                epic.setStatus(Status.DONE);
            } else if (allTasksAreNew) {
                epic.setStatus(Status.NEW);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }
}
