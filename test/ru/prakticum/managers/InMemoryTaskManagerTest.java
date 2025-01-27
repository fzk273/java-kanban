package ru.prakticum.managers;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.prakticum.enums.Status;
import ru.prakticum.interfaces.TaskManager;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;
import ru.prakticum.utils.Managers;

import java.util.ArrayList;

class InMemoryTaskManagerTest {
    private TaskManager inMemoryTaskManager;
    private Task task;
    private Epic epic;
    private SubTask subTask;

    @BeforeEach
    void init() {
        inMemoryTaskManager = Managers.getDefault();
        task = new Task("task", "desc");
        epic = new Epic("epic", "desc");
        subTask = new SubTask("Subtask", "desc1", 0);

    }

    @Test
    void tasksWithSameIdsAreEqual() {
        inMemoryTaskManager.createTask(task);
        Assertions.assertEquals(task, inMemoryTaskManager.getTaskById(0));
    }

    @Test
    void inheritorsOfTheTaskWithSameIdsAreEqual() {
        inMemoryTaskManager.createEpic(epic);
        Assertions.assertEquals(epic, inMemoryTaskManager.getEpicById(0));

        inMemoryTaskManager.createSubtask(subTask);
        Assertions.assertEquals(subTask, inMemoryTaskManager.getSubtaskById(1));
    }


    @Test
    void checkAllTasksSubtasksEpicsAreEmptyAfterCreation() {
        Assertions.assertTrue(inMemoryTaskManager.getTasks().isEmpty());
        Assertions.assertTrue(inMemoryTaskManager.getSubtasks().isEmpty());
        Assertions.assertTrue(inMemoryTaskManager.getEpics().isEmpty());
    }

    @Test
    void checkThatTasksWithGeneratedAndSetIdAreNotConflicting() {
        inMemoryTaskManager.createTask(task);
        task.setId(1);
        inMemoryTaskManager.createTask(task);
        Assertions.assertEquals(2, inMemoryTaskManager.getTasks().size());
    }

    @Test
    void createTask() {
        inMemoryTaskManager.createTask(task);
        Assertions.assertEquals(1, inMemoryTaskManager.getTasks().size());
    }

    @Test
    void createSubtask() {
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createSubtask(subTask);
        Assertions.assertEquals(1, inMemoryTaskManager.getSubtasks().size());
    }

    @Test
    void createEpic() {
        inMemoryTaskManager.createEpic(epic);
        Assertions.assertEquals(1, inMemoryTaskManager.getEpics().size());
    }

    @Test
    void getTasks() {
        inMemoryTaskManager.createTask(task);
        Assertions.assertEquals(1, inMemoryTaskManager.getTasks().size());

        inMemoryTaskManager.createTask(task);
        Assertions.assertEquals(2, inMemoryTaskManager.getTasks().size());
    }

    @Test
    void getSubtasks() {
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createSubtask(subTask);
        Assertions.assertEquals(1, inMemoryTaskManager.getSubtasks().size());

        inMemoryTaskManager.createSubtask(subTask);
        Assertions.assertEquals(2, inMemoryTaskManager.getSubtasks().size());
    }

    @Test
    void getEpics() {
        inMemoryTaskManager.createEpic(epic);
        Assertions.assertEquals(1, inMemoryTaskManager.getEpics().size());

        inMemoryTaskManager.createEpic(epic);
        Assertions.assertEquals(2, inMemoryTaskManager.getEpics().size());
    }

    @Test
    void getTaskById() {
        inMemoryTaskManager.createTask(task);
        Assertions.assertEquals(task, inMemoryTaskManager.getTaskById(0));
    }

    @Test
    void getSubtaskById() {
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createSubtask(subTask);
        Assertions.assertEquals(subTask, inMemoryTaskManager.getSubtaskById(1));
    }

    @Test
    void getEpicById() {
        inMemoryTaskManager.createEpic(epic);
        Assertions.assertEquals(epic, inMemoryTaskManager.getEpicById(0));
    }

    @Test
    void deleteTasks() {
        inMemoryTaskManager.createTask(task);
        inMemoryTaskManager.createTask(task);
        Assertions.assertEquals(2, inMemoryTaskManager.getTasks().size());

        inMemoryTaskManager.deleteTasks();
        Assertions.assertEquals(0, inMemoryTaskManager.getTasks().size());
    }

    @Test
    void deleteSubtasks() {
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createSubtask(subTask);
        inMemoryTaskManager.createSubtask(subTask);
        Assertions.assertEquals(2, inMemoryTaskManager.getSubtasks().size());

        inMemoryTaskManager.deleteSubtasks();
        Assertions.assertEquals(0, inMemoryTaskManager.getSubtasks().size());
    }

    @Test
    void deleteEpics() {
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createEpic(epic);
        Assertions.assertEquals(2, inMemoryTaskManager.getEpics().size());

        inMemoryTaskManager.deleteEpics();
        Assertions.assertEquals(0, inMemoryTaskManager.getEpics().size());
    }

    @Test
    void deleteTaskByID() {
        inMemoryTaskManager.createTask(task);
        Assertions.assertEquals(1, inMemoryTaskManager.getTasks().size());

        inMemoryTaskManager.deleteTaskByID(task.getId());
        Assertions.assertEquals(0, inMemoryTaskManager.getTasks().size());
    }

    @Test
    void deleteSubtaskById() {
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createSubtask(subTask);
        int subTaskId = subTask.getId();
        Assertions.assertEquals(1, inMemoryTaskManager.getSubtasks().size());

        inMemoryTaskManager.deleteSubtaskById(subTask.getId());
        Assertions.assertEquals(0, inMemoryTaskManager.getSubtasks().size());

        ArrayList<Integer> epicSubtasksIds = epic.getSubtaskIds();
        Assertions.assertFalse(epicSubtasksIds.contains(subTaskId));
    }

    @Test
    void deleteEpicById() {
        inMemoryTaskManager.createEpic(epic);
        Assertions.assertEquals(1, inMemoryTaskManager.getEpics().size());

        inMemoryTaskManager.deleteEpicById(epic.getId());
        Assertions.assertTrue(inMemoryTaskManager.getEpics().isEmpty());

    }

    @Test
    void updateTask() {
        inMemoryTaskManager.createTask(task);
        Assertions.assertEquals(1, inMemoryTaskManager.getTasks().size());

        task.setDescription("new desc");
        inMemoryTaskManager.updateTask(task);
        Assertions.assertEquals(task, inMemoryTaskManager.getTaskById(0));

    }

    @Test
    void updateSubtask() {
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createSubtask(subTask);
        Assertions.assertEquals(1, inMemoryTaskManager.getSubtasks().size());

        subTask.setDescription("new Desc");
        inMemoryTaskManager.updateSubtask(subTask);
        Assertions.assertEquals(subTask, inMemoryTaskManager.getSubtaskById(1));
    }

    @Test
    void updateEpic() {
        inMemoryTaskManager.createEpic(epic);
        Assertions.assertEquals(1, inMemoryTaskManager.getEpics().size());

        epic.setDescription("new Desc");
        inMemoryTaskManager.updateEpic(epic);
        Assertions.assertEquals(epic, inMemoryTaskManager.getEpicById(epic.getId()));
    }

    @Test
    void getEpicSubtasks() {
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createSubtask(subTask);
        Assertions.assertTrue(epic.getSubtaskIds().contains(subTask.getId()));
    }

    @Test
    void updateEpicStatus() {
        inMemoryTaskManager.createEpic(epic);
        Assertions.assertEquals(1, inMemoryTaskManager.getEpics().size());

        inMemoryTaskManager.createSubtask(subTask);
        Assertions.assertEquals(Status.NEW, epic.getStatus());

        subTask.setStatus(Status.IN_PROGRESS);
        inMemoryTaskManager.updateSubtask(subTask);

        SubTask subTask2 = new SubTask("Subtask", "desc2", epic.getId());
        inMemoryTaskManager.createSubtask(subTask2);
        Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus());

        subTask.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubtask(subTask);
        Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus());

        subTask2.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubtask(subTask2);
        Assertions.assertEquals(Status.DONE, epic.getStatus());
    }
}
