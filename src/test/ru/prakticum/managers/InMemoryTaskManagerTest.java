package ru.prakticum.managers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.prakticum.enums.Status;
import ru.prakticum.interfaces.TaskManager;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;
import ru.prakticum.utils.Managers;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(task, inMemoryTaskManager.getTaskById(0));
    }

    @Test
    void inheritorsOfTheTaskWithSameIdsAreEqual() {
        inMemoryTaskManager.createEpic(epic);
        assertEquals(epic, inMemoryTaskManager.getEpicById(0));

        inMemoryTaskManager.createSubtask(subTask);
        assertEquals(subTask, inMemoryTaskManager.getSubtaskById(1));
    }

    @Test
    void cannotPutEpicAsASubtaskInEpic() {
        //TODO я не понимаю как можно реализовать этот тест. у меня нет ни сеттеров для айдишников.
        // каст тоже не сработает. Создание подзадачи принимает объект подзадачи. нид хэлп
        inMemoryTaskManager.createEpic(epic);
        Epic epic1 = new Epic("epic1", "desc1");

    }

    @Test
    void checkThatSubtaskCannotBeItsOwnEpic() {
        //TODO поидее на такое должен вылететь эксепшн, но мы такого не проходили.
        // в коде метода есть "ошибка" и возвращается null. поэтому считаю тест валидным
        inMemoryTaskManager.createSubtask(subTask);
        assertNull(subTask.getId());
    }

    @Test
    void checkAllTasksSubtasksEpicsAreEmptyAfterCreation() {
        assertTrue(inMemoryTaskManager.getTasks().isEmpty());
        assertTrue(inMemoryTaskManager.getSubtasks().isEmpty());
        assertTrue(inMemoryTaskManager.getEpics().isEmpty());
    }

    @Test
    void checkThatTasksWithGeneratedAndSetIdAreNotConflicting() {
        inMemoryTaskManager.createTask(task);
        task.setId(1);
        inMemoryTaskManager.createTask(task);
        assertEquals(2, inMemoryTaskManager.getTasks().size());
    }

    @Test
    void checkThatAllFieldsOfTaskAreImmutableAfterItWasAddedToManager() {
        //TODO не понимаю почему они меняются в хэщмапе.
        // нужно записывать копию объекта в хэшмап?? если так то, подскажите пожалуйста как реализовать.
        // такой же вопрос к тесту ниже
        int id = 0;
        String description = "desc";
        inMemoryTaskManager.createTask(task);
        task.setDescription("new Desc");
        task.setId(100);
        assertEquals(id, inMemoryTaskManager.getTaskById(0).getId());
        assertEquals(description, inMemoryTaskManager.getTaskById(0).getDescription());
    }

    @Test
    void checkThatTaskStoreFirstStateOfTaskAfterItWasPutToManager() {
        Task newTask = inMemoryTaskManager.createTask(task);
        task.setDescription("new Desc");
        task.setId(100);
        assertNotEquals(newTask, task);
    }

    @Test
    void createTask() {
        inMemoryTaskManager.createTask(task);
        assertEquals(1, inMemoryTaskManager.getTasks().size());
    }

    @Test
    void createSubtask() {
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createSubtask(subTask);
        assertEquals(1, inMemoryTaskManager.getSubtasks().size());
    }

    @Test
    void createEpic() {
        inMemoryTaskManager.createEpic(epic);
        assertEquals(1, inMemoryTaskManager.getEpics().size());
    }

    @Test
    void getTasks() {
        inMemoryTaskManager.createTask(task);
        assertEquals(1, inMemoryTaskManager.getTasks().size());

        inMemoryTaskManager.createTask(task);
        assertEquals(2, inMemoryTaskManager.getTasks().size());
    }

    @Test
    void getSubtasks() {
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createSubtask(subTask);
        assertEquals(1, inMemoryTaskManager.getSubtasks().size());

        inMemoryTaskManager.createSubtask(subTask);
        assertEquals(2, inMemoryTaskManager.getSubtasks().size());
    }

    @Test
    void getEpics() {
        inMemoryTaskManager.createEpic(epic);
        assertEquals(1, inMemoryTaskManager.getEpics().size());

        inMemoryTaskManager.createEpic(epic);
        assertEquals(2, inMemoryTaskManager.getEpics().size());
    }

    @Test
    void getTaskById() {
        inMemoryTaskManager.createTask(task);
        assertEquals(task, inMemoryTaskManager.getTaskById(0));
    }

    @Test
    void getSubtaskById() {
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createSubtask(subTask);
        assertEquals(subTask, inMemoryTaskManager.getSubtaskById(1));
    }

    @Test
    void getEpicById() {
        inMemoryTaskManager.createEpic(epic);
        assertEquals(epic, inMemoryTaskManager.getEpicById(0));
    }

    @Test
    void deleteTasks() {
        inMemoryTaskManager.createTask(task);
        inMemoryTaskManager.createTask(task);
        assertEquals(2, inMemoryTaskManager.getTasks().size());

        inMemoryTaskManager.deleteTasks();
        assertEquals(0, inMemoryTaskManager.getTasks().size());
    }

    @Test
    void deleteSubtasks() {
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createSubtask(subTask);
        inMemoryTaskManager.createSubtask(subTask);
        assertEquals(2, inMemoryTaskManager.getSubtasks().size());

        inMemoryTaskManager.deleteSubtasks();
        assertEquals(0, inMemoryTaskManager.getSubtasks().size());
    }

    @Test
    void deleteEpics() {
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createEpic(epic);
        assertEquals(2, inMemoryTaskManager.getEpics().size());

        inMemoryTaskManager.deleteEpics();
        assertEquals(0, inMemoryTaskManager.getEpics().size());
    }

    @Test
    void deleteTaskByID() {
        inMemoryTaskManager.createTask(task);
        assertEquals(1, inMemoryTaskManager.getTasks().size());

        inMemoryTaskManager.deleteTaskByID(task.getId());
        assertEquals(0, inMemoryTaskManager.getTasks().size());
    }

    @Test
    void deleteSubtaskById() {
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createSubtask(subTask);
        int subTaskId = subTask.getId();
        assertEquals(1, inMemoryTaskManager.getSubtasks().size());

        inMemoryTaskManager.deleteSubtaskById(subTask.getId());
        assertEquals(0, inMemoryTaskManager.getSubtasks().size());

        ArrayList<Integer> epicSubtasksIds = epic.getSubtaskIds();
        assertFalse(epicSubtasksIds.contains(subTaskId));
    }

    @Test
    void deleteEpicById() {
        inMemoryTaskManager.createEpic(epic);
        assertEquals(1, inMemoryTaskManager.getEpics().size());

        inMemoryTaskManager.deleteEpicById(epic.getId());
        assertTrue(inMemoryTaskManager.getEpics().isEmpty());

    }

    @Test
    void updateTask() {
        inMemoryTaskManager.createTask(task);
        assertEquals(1, inMemoryTaskManager.getTasks().size());

        task.setDescription("new desc");
        inMemoryTaskManager.updateTask(task);
        assertEquals(task, inMemoryTaskManager.getTaskById(0));

    }

    @Test
    void updateSubtask() {
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createSubtask(subTask);
        assertEquals(1, inMemoryTaskManager.getSubtasks().size());

        subTask.setDescription("new Desc");
        inMemoryTaskManager.updateSubtask(subTask);
        assertEquals(subTask, inMemoryTaskManager.getSubtaskById(1));
    }

    @Test
    void updateEpic() {
        inMemoryTaskManager.createEpic(epic);
        assertEquals(1, inMemoryTaskManager.getEpics().size());

        epic.setDescription("new Desc");
        inMemoryTaskManager.updateEpic(epic);
        assertEquals(epic, inMemoryTaskManager.getEpicById(epic.getId()));
    }

    @Test
    void getEpicSubtasks() {
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.createSubtask(subTask);
        System.out.println(epic.getSubtaskIds());
        System.out.println(subTask.getId());
        assertTrue(epic.getSubtaskIds().contains(subTask.getId()));
    }

    @Test
    void updateEpicStatus() {
        inMemoryTaskManager.createEpic(epic);
        assertEquals(1, inMemoryTaskManager.getEpics().size());

        inMemoryTaskManager.createSubtask(subTask);
        assertEquals(Status.NEW, epic.getStatus());

        subTask.setStatus(Status.IN_PROGRESS);
        inMemoryTaskManager.updateSubtask(subTask);

        SubTask subTask2 = new SubTask("Subtask", "desc2", epic.getId());
        inMemoryTaskManager.createSubtask(subTask2);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());

        subTask.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubtask(subTask);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());

        subTask2.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubtask(subTask2);
        assertEquals(Status.DONE, epic.getStatus());
    }
}
