package ru.prakticum.managers;

import org.junit.jupiter.api.Test;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void add() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager(historyManager);
        assertTrue(historyManager.getHistory().isEmpty());

        Task task = new Task("task", "desc");
        inMemoryTaskManager.createTask(task);
        inMemoryTaskManager.getTaskById(task.getId());
        assertEquals(1, historyManager.getHistory().size());
        for (int i = 1; i <= 10; ++i) {
            Task task1 = new Task("task" + i, "desc" + i);
            inMemoryTaskManager.createTask(task1);
            inMemoryTaskManager.getTaskById(task1.getId());
        }
        assertEquals(10, historyManager.getHistory().size());

        Task lastTask = new Task("last task", "desc");
        inMemoryTaskManager.createTask(lastTask);
        inMemoryTaskManager.getTaskById(lastTask.getId());
        assertEquals(lastTask, historyManager.getHistory().getLast());

        Epic epic = new Epic("epic", "desc");
        inMemoryTaskManager.createEpic(epic);
        inMemoryTaskManager.getEpicById(epic.getId());
        assertEquals(epic, historyManager.getHistory().getLast());

        SubTask subTask = new SubTask("subtask", "desc", epic.getId());
        inMemoryTaskManager.createSubtask(subTask);
        inMemoryTaskManager.getSubtaskById(subTask.getId());
        assertEquals(subTask, historyManager.getHistory().getLast());
    }
}