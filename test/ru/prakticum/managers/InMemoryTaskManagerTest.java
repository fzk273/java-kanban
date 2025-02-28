package ru.prakticum.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.prakticum.tasks.Task;

import java.time.Duration;

class InMemoryTaskManagerTest extends TaskManagerTest {

    @BeforeEach
    void init() {
        taskManager = new InMemoryTaskManager();
        super.init();
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
        Task task1 = new Task("task", "desc");
        task1.setStartTime(nowDateTime.plus(Duration.ofHours(2)));
        task1.setDuration(oneHour);
        inMemoryTaskManager.createTask(task1);
        Assertions.assertEquals(2, inMemoryTaskManager.getTasks().size());
    }

    @Test
    public void checkTasksSorting() {
        taskManager.createEpic(epic);
        taskManager.createTask(task);
        taskManager.createSubtask(subTask);
        Task task1 = new Task("task1", "desc1");
        task1.setStartTime(nowDateTime.plusHours(4));
        task1.setDuration(Duration.ofHours(1));
        taskManager.createTask(task1);
        Assertions.assertEquals(taskManager.getPrioritizedTasks().size(), 3);
        Assertions.assertEquals(taskManager.getPrioritizedTasks().getFirst(), task);
        Assertions.assertEquals(taskManager.getPrioritizedTasks().getLast(), task1);

    }

}
