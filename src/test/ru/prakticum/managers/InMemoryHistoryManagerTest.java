package ru.prakticum.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.prakticum.interfaces.HistoryManager;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;
import ru.prakticum.utils.Managers;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;
    private Task task;
    private Epic epic;
    private SubTask subTask;

    @BeforeEach
    void init() {
        historyManager = Managers.getDefaultHistory();
        task = new Task("task", "desc");
        epic = new Epic("epic", "desc");
        subTask = new SubTask("subtask", "desc", 1);
    }

    @Test
    void historyIsEmptyAfterCreation() {
        assertTrue(historyManager.getHistory().isEmpty());
    }

    @Test
    void addOneTaskToHistory() {
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void historyCanNotBeGreaterThan10() {
        for (int i = 1; i <= 11; ++i) {
            historyManager.add(task);
        }
        assertEquals(10, historyManager.getHistory().size());
    }

    @Test
    void newTaskAppendsToTheEndOfTheHistory() {
        historyManager.add(task);
        Task lastTask = new Task("last task", "desc");
        historyManager.add(lastTask);
        assertEquals(lastTask, historyManager.getHistory().getLast());
    }

    @Test
    void canAddEpicToHistory() {
        historyManager.add(epic);
        assertEquals(epic, historyManager.getHistory().getLast());
    }

    @Test
    void canAddSubtaskToHistory() {
        historyManager.add(subTask);
        assertEquals(subTask, historyManager.getHistory().getLast());
    }
}