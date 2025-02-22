package ru.prakticum.managers;

import ru.prakticum.enums.Status;
import ru.prakticum.interfaces.HistoryManager;
import ru.prakticum.interfaces.TaskManager;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;
import ru.prakticum.utils.Managers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    protected Integer counter;
    protected HashMap<Integer, Task> tasks;
    protected HashMap<Integer, SubTask> subtasks;
    protected HashMap<Integer, Epic> epics;
    private HistoryManager history;

    public InMemoryTaskManager() {
        counter = 0;
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        history = Managers.getDefaultHistory();
    }


    @Override
    public Task createTask(Task task) {
        if (taskTimelineValidation(task)) {
            throw new IllegalArgumentException("Ошибка: время выполнения задачи пересекается с другой задачей");
        }
        task.setId(counter);
        tasks.put(counter, task);
        counter++;
        return task;
    }

    @Override
    public SubTask createSubtask(SubTask subTask) {
        if (taskTimelineValidation(subTask)) {
            throw new IllegalArgumentException("Ошибка: время выполнения задачи пересекается с другой задачей");
        }
        Epic epic = epics.get(subTask.getEpicId());
        if (epic == null) {
            System.out.println("ERROR: There is no epic with id: " + subTask.getEpicId());
            return null;
        }
        subTask.setId(counter);
        subtasks.put(counter, subTask);
        epics.get(subTask.getEpicId()).addSubtask(counter);
        updateEpicStatus(epic);
        updateEpicDateTime(epic);
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
        history.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public SubTask getSubtaskById(Integer id) {
        history.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public Epic getEpicById(Integer id) {
        history.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public void deleteTasks() {
        for (Integer taskId : tasks.keySet()) {
            history.remove(taskId);
        }
        tasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        for (Integer subtaskId : subtasks.keySet()) {
            history.remove(subtaskId);
        }
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            epic.setStatus(Status.NEW);
        }
        subtasks.clear();
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteTaskByID(Integer id) {
        tasks.remove(id);
        history.remove(id);
    }

    @Override
    public void deleteSubtaskById(Integer id) {
        SubTask subTask = subtasks.get(id);
        Epic epic = epics.get(subTask.getEpicId());
        epic.getSubtaskIds().remove(id);
        subtasks.remove(id);
        history.remove(id);
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
            history.remove(id);
        }
        epics.remove(id);
        history.remove(id);
    }

    @Override
    public void updateTask(Task task) {
        if (taskTimelineValidation(task)) {
            throw new IllegalArgumentException("Ошибка: время выполнения задачи пересекается с другой задачей");
        }
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateSubtask(SubTask subtask) {
        if (taskTimelineValidation(subtask)) {
            throw new IllegalArgumentException("Ошибка: время выполнения задачи пересекается с другой задачей");
        }
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(epics.get(subtask.getEpicId()));
        updateEpicDateTime(epics.get(subtask.getEpicId()));
        updateEpicDuration(epics.get(subtask.getEpicId()));
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
            epicsSubtasks.add(subtasks.get(subtaskId));
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

    public void updateEpicDuration(Epic epic) {
        Duration epicDuration = getEpicSubtasks(epic).stream()
                .map(SubTask::getDuration)
                .filter(Objects::nonNull)
                .reduce(Duration.ZERO, Duration::plus);
        epic.setDuration(epicDuration);
    }

    public void updateEpicDateTime(Epic epic) {
        LocalDateTime earlistSubtask = getEpicSubtasks(epic).stream()
                .min(Comparator.comparing(Task::getStartTime)).get().getStartTime();
        LocalDateTime latestSubtask = getEpicSubtasks(epic).stream()
                .max(Comparator.comparing(Task::getEndTime)).get().getStartTime();
        epic.setStartTime(earlistSubtask);
        epic.setEndTime(latestSubtask);

    }

    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }

    public List<Task> getPrioritizedTasks() {
        Comparator<Task> taskComparator = Comparator.comparing(Task::getStartTime, Comparator.nullsLast(Comparator.naturalOrder()));
        TreeSet<Task> sortedSet = new TreeSet<>(taskComparator);
        sortedSet.addAll(tasks.values());
        sortedSet.addAll(subtasks.values());
        return sortedSet.stream().toList();
    }

    public boolean taskTimelineValidation(Task task) {
        List<Task> taskByPriority = getPrioritizedTasks();
        return taskByPriority.stream().anyMatch(taskFromStream -> isOverlaping(taskFromStream, task));
    }

    public boolean isOverlaping(Task task1, Task task2) {
        if (task1.getStartTime() == null || task2.getStartTime() == null || task1.getEndTime() == null || task2.getEndTime() == null)
            return false;
        return !(task1.getEndTime().isBefore(task2.getStartTime()) || task2.getEndTime().isBefore(task1.getStartTime()));
    }
}
