package ru.prakticum.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.prakticum.tasks.Task;

import java.io.IOException;
import java.time.Duration;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    void init() throws IOException {
        taskManager = new InMemoryTaskManager();
        super.init();
    }

    @Test
    void tasksWithSameIdsAreEqual() {
        taskManager.createTask(task);
        Assertions.assertEquals(task, taskManager.getTaskById(0));
    }

    @Test
    void inheritorsOfTheTaskWithSameIdsAreEqual() {
        taskManager.createEpic(epic);
        Assertions.assertEquals(epic, taskManager.getEpicById(0));

        taskManager.createSubtask(subTask);
        Assertions.assertEquals(subTask, taskManager.getSubtaskById(1));
    }


    @Test
    void checkAllTasksSubtasksEpicsAreEmptyAfterCreation() {
        Assertions.assertTrue(taskManager.getTasks().isEmpty());
        Assertions.assertTrue(taskManager.getSubtasks().isEmpty());
        Assertions.assertTrue(taskManager.getEpics().isEmpty());
    }

    @Test
    void checkThatTasksWithGeneratedAndSetIdAreNotConflicting() {
        taskManager.createTask(task);
        task.setId(1);
        Task task1 = new Task("task", "desc");
        task1.setStartTime(nowDateTime.plus(Duration.ofHours(2)));
        task1.setDuration(oneHour);
        taskManager.createTask(task1);
        Assertions.assertEquals(2, taskManager.getTasks().size());
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
