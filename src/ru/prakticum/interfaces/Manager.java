package ru.prakticum.interfaces;

import ru.prakticum.enums.Status;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;

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

    public Task createTask(Task task) {
        task.setId(counter);
        tasks.put(counter, task);
        counter++;
        return task;
    }

    public SubTask createSubtask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());
        if (epic == null) {
            System.out.println("ERROR: There is no epic with id: " + subTask.getEpicId());
            return null;
        }
        subTask.setId(counter);
        subtasks.put(counter, subTask);
        Epic epicToUpdate = epics.get(subTask.getEpicId());
        ArrayList<Integer> subtasksIds = epicToUpdate.getSubtaskIds();
        subtasksIds.add(counter);
        updateEpic(epicToUpdate);
        counter++;
        return subTask;
    }

    public Epic createEpic(Epic epic) {
        epic.setId(counter);
        epics.put(counter, epic);
        counter++;
        return epic;
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<SubTask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
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
        return tasks.isEmpty();
    }

    public boolean deleteSubtasks() {
        //TODO this looks much better. Thanks!
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            epic.setStatus(Status.NEW);
        }
        subtasks.clear();
        return subtasks.isEmpty();
    }

    public boolean deleteEpics() {
        epics.clear();
        subtasks.clear();
        return epics.isEmpty();
    }

    public void deleteTaskByID(Integer id) {
        tasks.remove(id);
    }

    public void deleteSubtaskById(Integer id) {
        SubTask subTask = getSubtaskById(id);
        Epic epic = getEpicById(subTask.getEpicId());
        epic.getSubtaskIds().remove(id);
        subtasks.remove(id);
        updateEpic(epic);
    }

    public void deleteEpicById(Integer id) {
        Epic epic = epics.remove(id);
        if (epic == null) {
            return;
        }
        for (Integer subtaskId : epic.getSubtaskIds()) {
            subtasks.remove(subtaskId);
        }
        epics.remove(id);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void updateSubtask(SubTask subtask) {
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(getEpicById(subtask.getEpicId()));
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
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
                if (!subTask.getStatus().equals(Status.NEW)) {
                    allTasksAreNew = false;
                }
                if (!subTask.getStatus().equals(Status.DONE)) {
                    allTasksAreDone = false;
                }
            }
            if (allTasksAreDone) {
                epic.setStatus(Status.DONE);
            } else if (allTasksAreNew) {
                epic.setStatus(Status.NEW);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }
}
