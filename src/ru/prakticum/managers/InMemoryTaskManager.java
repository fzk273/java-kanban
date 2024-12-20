package ru.prakticum.managers;

import ru.prakticum.enums.Status;
import ru.prakticum.interfaces.Manager;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements Manager {
    private Integer counter;
    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, SubTask> subtasks;
    private HashMap<Integer, Epic> epics;
    private final ArrayList<Task> history;
    private final int HISTORY_SIZE = 10;

    public InMemoryTaskManager() {
        counter = 0;
        tasks = new HashMap();
        subtasks = new HashMap();
        epics = new HashMap();
        history = new ArrayList<>();
    }

    @Override
    public Task createTask(Task task) {
        task.setId(counter);
        tasks.put(counter, task);
        counter++;
        return task;
    }

    @Override
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

    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(counter);
        epics.put(counter, epic);
        counter++;
        return epic;
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<SubTask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Task getTaskById(Integer id) {
        addToHistory(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public SubTask getSubtaskById(Integer id) {
        addToHistory(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public Epic getEpicById(Integer id) {
        addToHistory(epics.get(id));
        return epics.get(id);
    }

    @Override
    public boolean deleteTasks() {
        tasks.clear();
        return tasks.isEmpty();
    }

    @Override
    public boolean deleteSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            epic.setStatus(Status.NEW);
        }
        subtasks.clear();
        return subtasks.isEmpty();
    }

    @Override
    public boolean deleteEpics() {
        epics.clear();
        subtasks.clear();
        return epics.isEmpty();
    }

    @Override
    public void deleteTaskByID(Integer id) {
        tasks.remove(id);
    }

    @Override
    public void deleteSubtaskById(Integer id) {
        SubTask subTask = getSubtaskById(id);
        Epic epic = getEpicById(subTask.getEpicId());
        epic.getSubtaskIds().remove(id);
        subtasks.remove(id);
        updateEpic(epic);
    }

    @Override
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

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateSubtask(SubTask subtask) {
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(getEpicById(subtask.getEpicId()));
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public ArrayList<SubTask> getEpicSubtasks(Epic epic) {
        ArrayList<SubTask> epicsSubtasks = new ArrayList<>();
        ArrayList<Integer> epicSubtasksIds = epic.getSubtaskIds();
        for (Integer subtaskId : epicSubtasksIds) {
            epicsSubtasks.add(getSubtaskById(subtaskId));
        }
        return epicsSubtasks;
    }

    @Override
    public void updateEpicStatus(Epic epic) {
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


    @Override
    public ArrayList<Task> getHistory() {
        return history;
    }

    boolean isHistoryFull() {
        return history.size() >= HISTORY_SIZE;
    }

    <T extends Task> void addToHistory(T task) {
        if (isHistoryFull()) {
            history.removeFirst();
            history.add(task);
        } else {
            history.add(task);
        }
    }

}
