package ru.prakticum.managers;


import org.junit.jupiter.api.Test;
import ru.prakticum.enums.Status;
import ru.prakticum.interfaces.Manager;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;
import ru.prakticum.utils.Managers;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    @Test
    void createTask() {
        Manager inMemoryTaskManager = Managers.getDefault();
        assertTrue(inMemoryTaskManager.getTasks().isEmpty());

        Task task1 = new Task("task1", "desc1");
        inMemoryTaskManager.createTask(task1);

        assertEquals(1, inMemoryTaskManager.getTasks().size());
    }

    @Test
    void createSubtask() {
        Manager inMemoryTaskManager = Managers.getDefault();
        assertTrue(inMemoryTaskManager.getSubtasks().isEmpty());

        Epic epic = new Epic("epic", "desc");
        inMemoryTaskManager.createEpic(epic);
        SubTask subTask = new SubTask("Subtask", "desc1", epic.getId());
        inMemoryTaskManager.createSubtask(subTask);
        assertEquals(1, inMemoryTaskManager.getSubtasks().size());
    }

    @Test
    void createEpic() {
        Manager inMemoryTaskManager = Managers.getDefault();
        assertTrue(inMemoryTaskManager.getEpics().isEmpty());
        Epic epic = new Epic("epic", "desc");
        inMemoryTaskManager.createEpic(epic);
        assertEquals(1, inMemoryTaskManager.getEpics().size());


    }

    @Test
    void getTasks() {
        Manager inMemoryTaskManager = Managers.getDefault();
        assertTrue(inMemoryTaskManager.getTasks().isEmpty());

        Task task1 = new Task("task1", "desc1");
        inMemoryTaskManager.createTask(task1);
        assertEquals(1, inMemoryTaskManager.getTasks().size());

        Task task2 = new Task("task1", "desc1");
        inMemoryTaskManager.createTask(task2);
        assertEquals(2, inMemoryTaskManager.getTasks().size());

    }

    @Test
    void getSubtasks() {
        Manager inMemoryTaskManager = Managers.getDefault();
        assertTrue(inMemoryTaskManager.getSubtasks().isEmpty());

        Epic epic = new Epic("epic", "desc");
        inMemoryTaskManager.createEpic(epic);
        SubTask subTask1 = new SubTask("Subtask", "desc1", epic.getId());
        inMemoryTaskManager.createSubtask(subTask1);
        assertEquals(1, inMemoryTaskManager.getSubtasks().size());

        SubTask subTask2 = new SubTask("Subtask", "desc1", epic.getId());
        inMemoryTaskManager.createSubtask(subTask2);
        assertEquals(2, inMemoryTaskManager.getSubtasks().size());
    }

    @Test
    void getEpics() {
        Manager inMemoryTaskManager = Managers.getDefault();
        assertTrue(inMemoryTaskManager.getEpics().isEmpty());

        Epic epic1 = new Epic("epic", "desc");
        inMemoryTaskManager.createEpic(epic1);
        assertEquals(1, inMemoryTaskManager.getEpics().size());

        Epic epic2 = new Epic("epic", "desc");
        inMemoryTaskManager.createEpic(epic2);
        assertEquals(2, inMemoryTaskManager.getEpics().size());
    }

    @Test
    void getTaskById() {
        Manager inMemoryTaskManager = Managers.getDefault();
        Task task1 = new Task("task1", "desc1");
        inMemoryTaskManager.createTask(task1);
        assertEquals(task1, inMemoryTaskManager.getTaskById(0));
    }

    @Test
    void getSubtaskById() {
        Manager inMemoryTaskManager = Managers.getDefault();

        Epic epic = new Epic("epic", "desc");
        inMemoryTaskManager.createEpic(epic);
        SubTask subTask = new SubTask("Subtask", "desc1", epic.getId());
        inMemoryTaskManager.createSubtask(subTask);
        assertEquals(subTask, inMemoryTaskManager.getSubtaskById(1));
    }

    @Test
    void getEpicById() {
        Manager inMemoryTaskManager = Managers.getDefault();

        Epic epic = new Epic("epic", "desc");
        inMemoryTaskManager.createEpic(epic);
        assertEquals(epic, inMemoryTaskManager.getEpicById(0));
    }

    @Test
    void deleteTasks() {
        Manager inMemoryTaskManager = Managers.getDefault();
        assertTrue(inMemoryTaskManager.getTasks().isEmpty());

        Task task1 = new Task("task1", "desc1");
        inMemoryTaskManager.createTask(task1);
        Task task2 = new Task("task1", "desc1");
        inMemoryTaskManager.createTask(task2);

        assertEquals(2, inMemoryTaskManager.getTasks().size());

        inMemoryTaskManager.deleteTasks();
        assertEquals(0, inMemoryTaskManager.getTasks().size());

    }

    @Test
    void deleteSubtasks() {
        Manager inMemoryTaskManager = Managers.getDefault();
        assertTrue(inMemoryTaskManager.getSubtasks().isEmpty());

        Epic epic = new Epic("epic", "desc");
        inMemoryTaskManager.createEpic(epic);
        SubTask subTask1 = new SubTask("Subtask", "desc1", epic.getId());
        inMemoryTaskManager.createSubtask(subTask1);
        SubTask subTask2 = new SubTask("Subtask", "desc1", epic.getId());
        inMemoryTaskManager.createSubtask(subTask2);
        assertEquals(2, inMemoryTaskManager.getSubtasks().size());

        inMemoryTaskManager.deleteSubtasks();
        assertEquals(0, inMemoryTaskManager.getSubtasks().size());

    }

    @Test
    void deleteEpics() {
        Manager inMemoryTaskManager = Managers.getDefault();
        assertTrue(inMemoryTaskManager.getEpics().isEmpty());

        Epic epic1 = new Epic("epic", "desc");
        inMemoryTaskManager.createEpic(epic1);
        Epic epic2 = new Epic("epic", "desc");
        inMemoryTaskManager.createEpic(epic2);
        assertEquals(2, inMemoryTaskManager.getEpics().size());

        inMemoryTaskManager.deleteEpics();
        assertEquals(0, inMemoryTaskManager.getEpics().size());


    }

    @Test
    void deleteTaskByID() {
        Manager inMemoryTaskManager = Managers.getDefault();
        assertTrue(inMemoryTaskManager.getTasks().isEmpty());

        Task task1 = new Task("task1", "desc1");
        inMemoryTaskManager.createTask(task1);
        assertEquals(1, inMemoryTaskManager.getTasks().size());

        inMemoryTaskManager.deleteTaskByID(task1.getId());
        assertEquals(0, inMemoryTaskManager.getTasks().size());

    }

    @Test
    void deleteSubtaskById() {
        Manager inMemoryTaskManager = Managers.getDefault();
        assertTrue(inMemoryTaskManager.getSubtasks().isEmpty());

        Epic epic = new Epic("epic", "desc");
        inMemoryTaskManager.createEpic(epic);
        SubTask subTask1 = new SubTask("Subtask", "desc1", epic.getId());
        inMemoryTaskManager.createSubtask(subTask1);
        int subTaskId = subTask1.getId();
        assertEquals(1, inMemoryTaskManager.getSubtasks().size());

        inMemoryTaskManager.deleteSubtaskById(subTask1.getId());
        assertEquals(0, inMemoryTaskManager.getSubtasks().size());

        ArrayList<Integer> epicSubtasksIds = epic.getSubtaskIds();
        assertFalse(epicSubtasksIds.contains(subTaskId));


    }

    @Test
    void deleteEpicById() {
        Manager inMemoryTaskManager = Managers.getDefault();
        assertTrue(inMemoryTaskManager.getEpics().isEmpty());

        Epic epic1 = new Epic("epic", "desc");
        inMemoryTaskManager.createEpic(epic1);
        assertEquals(1, inMemoryTaskManager.getEpics().size());
        inMemoryTaskManager.deleteEpicById(epic1.getId());
        assertTrue(inMemoryTaskManager.getEpics().isEmpty());

    }

    @Test
    void updateTask() {
        Manager inMemoryTaskManager = Managers.getDefault();
        assertTrue(inMemoryTaskManager.getTasks().isEmpty());

        Task task1 = new Task("task1", "desc1");
        inMemoryTaskManager.createTask(task1);
        assertEquals(1, inMemoryTaskManager.getTasks().size());
        task1.setDescription("new desc");

        inMemoryTaskManager.updateTask(task1);
        assertEquals(task1, inMemoryTaskManager.getTaskById(0));

    }

    @Test
    void updateSubtask() {
        Manager inMemoryTaskManager = Managers.getDefault();
        assertTrue(inMemoryTaskManager.getSubtasks().isEmpty());

        Epic epic = new Epic("epic", "desc");
        inMemoryTaskManager.createEpic(epic);
        SubTask subTask1 = new SubTask("Subtask", "desc1", epic.getId());
        inMemoryTaskManager.createSubtask(subTask1);
        assertEquals(1, inMemoryTaskManager.getSubtasks().size());

        subTask1.setDescription("new Desc");
        inMemoryTaskManager.updateSubtask(subTask1);
        assertEquals(subTask1, inMemoryTaskManager.getSubtaskById(1));
    }

    @Test
    void updateEpic() {
        Manager inMemoryTaskManager = Managers.getDefault();
        assertTrue(inMemoryTaskManager.getEpics().isEmpty());

        Epic epic1 = new Epic("epic", "desc");
        inMemoryTaskManager.createEpic(epic1);
        assertEquals(1, inMemoryTaskManager.getEpics().size());

        epic1.setDescription("new Desc");
        inMemoryTaskManager.updateEpic(epic1);
        assertEquals(epic1, inMemoryTaskManager.getEpicById(epic1.getId()));


    }

    @Test
    void getEpicSubtasks() {
        Manager inMemoryTaskManager = Managers.getDefault();
        assertTrue(inMemoryTaskManager.getEpics().isEmpty());

        Epic epic1 = new Epic("epic", "desc");
        inMemoryTaskManager.createEpic(epic1);
        SubTask subTask1 = new SubTask("Subtask", "desc1", epic1.getId());
        inMemoryTaskManager.createSubtask(subTask1);
        assertTrue(epic1.getSubtaskIds().contains(subTask1.getId()));
    }

    @Test
    void updateEpicStatus() {
        Manager inMemoryTaskManager = Managers.getDefault();
        assertTrue(inMemoryTaskManager.getEpics().isEmpty());

        Epic epic1 = new Epic("epic", "desc");
        inMemoryTaskManager.createEpic(epic1);
        assertEquals(1, inMemoryTaskManager.getEpics().size());

        SubTask subTask1 = new SubTask("Subtask", "desc1", epic1.getId());
        inMemoryTaskManager.createSubtask(subTask1);
        assertEquals(Status.NEW, epic1.getStatus());

        subTask1.setStatus(Status.IN_PROGRESS);
        inMemoryTaskManager.updateSubtask(subTask1);

        SubTask subTask2 = new SubTask("Subtask", "desc2", epic1.getId());
        inMemoryTaskManager.createSubtask(subTask2);
        assertEquals(Status.IN_PROGRESS, epic1.getStatus());


        subTask1.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubtask(subTask1);
        assertEquals(Status.IN_PROGRESS, epic1.getStatus());

        subTask2.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubtask(subTask2);
        assertEquals(Status.DONE, epic1.getStatus());

    }

}
