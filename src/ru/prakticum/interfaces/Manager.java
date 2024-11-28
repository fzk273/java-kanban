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
        if (getEpicById(subTask.getEpicId()) != null) {
            subTask.setId(counter);
            subtasks.put(counter, subTask);
            Epic epicToUpdate = getEpicById(subTask.getEpicId());
            ArrayList<Integer> subtasksIds = epicToUpdate.getSubtaskIds();
            subtasksIds.add(counter);
            updateEpic(epicToUpdate);
            counter++;
        } else {
            System.out.println("ERROR: There is no epic with id: " + subTask.getEpicId());
        }
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
        //TODO i dont know how it could be implemented better. need help
        for (SubTask subTask : subtasks.values()) {
            Integer subTaskId = subTask.getId();
            Integer epicId = subTask.getEpicId();
            String epicName = epics.get(epicId).getName();
            String epicDescription = epics.get(epicId).getDescription();
            ArrayList<Integer> epicSubtasks = epics.get(epicId).getSubtaskIds();
            epicSubtasks.remove(subTaskId);
            Epic epic = new Epic(epicName, epicDescription);
            epic.getSubtaskIds().addAll(epicSubtasks);
            deleteSubtaskById(subTaskId);
            updateEpic(epic);

        }
        return subtasks.isEmpty();
    }

    public boolean deleteEpics() {
        for (Epic epic : epics.values()) {
            for (Integer id : epic.getSubtaskIds()) {
                if (getSubtaskById(id) != null) {
                    deleteSubtaskById(id);
                    //TODO im not sure should i do an epic status check after deletion of a subtask???
                } else {
                    System.out.println("ERROR: There is no subtask with id: " + id);
                }
            }
        }
        epics.clear();
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
        //TODO i removed the logic for subtask deletion, but in this case all subtasks gonna be without parent
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
        //TODO I dont agree with updating name and description. i still need to update subtasks ids in some cases and
        // this leads to update all fields in the instance. its easier to do it the way its implemented now.
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
