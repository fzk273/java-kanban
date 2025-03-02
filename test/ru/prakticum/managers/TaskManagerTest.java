package ru.prakticum.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.prakticum.enums.Status;
import ru.prakticum.interfaces.TaskManager;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;
    protected Task task;
    protected Epic epic;
    protected SubTask subTask;
    protected LocalDateTime nowDateTime;
    protected Duration oneHour;

    @BeforeEach
    void init() throws IOException {
        nowDateTime = LocalDateTime.now();
        oneHour = Duration.ofHours(1);
        task = new Task("task", "desc");
        task.setStartTime(nowDateTime);
        task.setDuration(oneHour);
        epic = new Epic("epic", "desc");
        epic.setStartTime(nowDateTime);
        epic.setDuration(oneHour);
        subTask = new SubTask("Subtask", "desc1", 0);
        subTask.setStartTime(nowDateTime.plus(Duration.ofHours(2)));
        subTask.setDuration(oneHour);
    }

    @Test
    void createTask() {
        taskManager.createTask(task);
        Assertions.assertEquals(1, taskManager.getTasks().size());
    }

    @Test
    void createSubtask() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subTask);
        Assertions.assertEquals(1, taskManager.getSubtasks().size());
    }

    @Test
    void createEpic() {
        taskManager.createEpic(epic);
        Assertions.assertEquals(1, taskManager.getEpics().size());
    }

    @Test
    void getTasks() {
        taskManager.createTask(task);
        Assertions.assertEquals(1, taskManager.getTasks().size());
        Task task1 = new Task("new sub", "new sub");
        task1.setDuration(oneHour);
        task1.setStartTime(nowDateTime.plus(Duration.ofHours(2)));
        taskManager.createTask(task1);
        Assertions.assertEquals(2, taskManager.getTasks().size());
    }

    @Test
    void getSubtasks() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subTask);
        Assertions.assertEquals(1, taskManager.getSubtasks().size());

        SubTask subTask1 = new SubTask("new sub", "new sub", 0);
        subTask1.setDuration(oneHour);
        subTask1.setStartTime(nowDateTime.plus(Duration.ofHours(4)));
        taskManager.createSubtask(subTask1);
        Assertions.assertEquals(2, taskManager.getSubtasks().size());
    }

    @Test
    void getEpics() {
        taskManager.createEpic(epic);
        Assertions.assertEquals(1, taskManager.getEpics().size());

        taskManager.createEpic(epic);
        Assertions.assertEquals(2, taskManager.getEpics().size());
    }

    @Test
    void getTaskById() {
        taskManager.createTask(task);
        Assertions.assertEquals(task, taskManager.getTaskById(0));
    }

    @Test
    void getSubtaskById() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subTask);
        Assertions.assertEquals(subTask, taskManager.getSubtaskById(1));
    }

    @Test
    void getEpicById() {
        taskManager.createEpic(epic);
        Assertions.assertEquals(epic, taskManager.getEpicById(0));
    }

    @Test
    void deleteTasks() {
        taskManager.createTask(task);
        Task task1 = new Task("new sub", "new sub");
        task1.setDuration(oneHour);
        task1.setStartTime(nowDateTime.plus(Duration.ofHours(2)));
        taskManager.createTask(task1);
        Assertions.assertEquals(2, taskManager.getTasks().size());

        taskManager.deleteTasks();
        Assertions.assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void deleteSubtasks() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subTask);
        SubTask subTask1 = new SubTask("new sub", "new sub", 0);
        subTask1.setDuration(oneHour);
        subTask1.setStartTime(nowDateTime.plus(Duration.ofHours(4)));
        taskManager.createSubtask(subTask1);
        Assertions.assertEquals(2, taskManager.getSubtasks().size());

        taskManager.deleteSubtasks();
        Assertions.assertEquals(0, taskManager.getSubtasks().size());
    }

    @Test
    void deleteEpics() {
        taskManager.createEpic(epic);
        taskManager.createEpic(epic);
        Assertions.assertEquals(2, taskManager.getEpics().size());

        taskManager.deleteEpics();
        Assertions.assertEquals(0, taskManager.getEpics().size());
    }

    @Test
    void deleteTaskByID() {
        taskManager.createTask(task);
        Assertions.assertEquals(1, taskManager.getTasks().size());

        taskManager.deleteTaskByID(task.getId());
        Assertions.assertEquals(0, taskManager.getTasks().size());
    }

    @Test
    void deleteSubtaskById() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subTask);
        int subTaskId = subTask.getId();
        Assertions.assertEquals(1, taskManager.getSubtasks().size());

        taskManager.deleteSubtaskById(subTask.getId());
        Assertions.assertEquals(0, taskManager.getSubtasks().size());

        ArrayList<Integer> epicSubtasksIds = epic.getSubtaskIds();
        Assertions.assertFalse(epicSubtasksIds.contains(subTaskId));
    }

    @Test
    void deleteEpicById() {
        taskManager.createEpic(epic);
        Assertions.assertEquals(1, taskManager.getEpics().size());

        taskManager.deleteEpicById(epic.getId());
        Assertions.assertTrue(taskManager.getEpics().isEmpty());

    }

    @Test
    void updateTask() {
        taskManager.createTask(task);
        Assertions.assertEquals(1, taskManager.getTasks().size());

        task.setDescription("new desc");
        taskManager.updateTask(task);
        Assertions.assertEquals(task, taskManager.getTaskById(0));

    }

    @Test
    void updateSubtask() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subTask);
        Assertions.assertEquals(1, taskManager.getSubtasks().size());

        subTask.setDescription("new Desc");
        taskManager.updateSubtask(subTask);
        Assertions.assertEquals(subTask, taskManager.getSubtaskById(1));
    }

    @Test
    void updateEpic() {
        taskManager.createEpic(epic);
        Assertions.assertEquals(1, taskManager.getEpics().size());

        epic.setDescription("new Desc");
        taskManager.updateEpic(epic);
        Assertions.assertEquals(epic, taskManager.getEpicById(epic.getId()));
    }

    @Test
    void getEpicSubtasks() {
        taskManager.createEpic(epic);
        taskManager.createSubtask(subTask);
        Assertions.assertTrue(epic.getSubtaskIds().contains(subTask.getId()));
    }

    @Test
    void updateEpicStatus() {
        taskManager.createEpic(epic);
        Assertions.assertEquals(1, taskManager.getEpics().size());

        taskManager.createSubtask(subTask);
        Assertions.assertEquals(Status.NEW, epic.getStatus());

        subTask.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubtask(subTask);

        SubTask subTask2 = new SubTask("Subtask", "desc2", epic.getId());
        subTask2.setStartTime(nowDateTime.plusHours(4));
        subTask2.setDuration(oneHour);
        taskManager.createSubtask(subTask2);
        Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus());

        subTask.setStatus(Status.DONE);
        taskManager.updateSubtask(subTask);
        Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus());

        subTask2.setStatus(Status.DONE);
        taskManager.updateSubtask(subTask2);
        Assertions.assertEquals(Status.DONE, epic.getStatus());
    }
}