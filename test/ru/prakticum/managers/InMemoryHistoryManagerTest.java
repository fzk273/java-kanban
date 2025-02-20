package ru.prakticum.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.prakticum.interfaces.HistoryManager;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;
import ru.prakticum.utils.Managers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;
    private Task task;
    private Epic epic;
    private SubTask subTask;
    private LocalDateTime nowDateTime;
    private Duration oneHour;

    @BeforeEach
    void init() {
        nowDateTime = LocalDateTime.now();
        oneHour = Duration.ofHours(1);
        historyManager = Managers.getDefaultHistory();
        task = new Task("task", "desc", nowDateTime, oneHour);
        task.setId(0);
        epic = new Epic("epic", "desc", nowDateTime, oneHour);
        epic.setId(1);
        subTask = new SubTask("subtask", "desc", nowDateTime, oneHour, 1);
        subTask.setId(2);
    }

    @Test
    void historyIsEmptyAfterCreation() {
        Assertions.assertTrue(historyManager.getHistory().isEmpty());
    }

    @Test
    void addOneTaskToHistory() {
        historyManager.add(task);
        Assertions.assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void newTaskAppendsToTheEndOfTheHistory() {
        historyManager.add(task);
        Task lastTask = new Task("last task", "desc", nowDateTime, oneHour);
        historyManager.add(lastTask);
        Assertions.assertEquals(lastTask, historyManager.getHistory().getLast());
    }

    @Test
    void canAddEpicToHistory() {
        historyManager.add(epic);
        Assertions.assertEquals(epic, historyManager.getHistory().getLast());
    }

    @Test
    void canAddSubtaskToHistory() {
        historyManager.add(subTask);
        Assertions.assertEquals(subTask, historyManager.getHistory().getLast());
    }

    @Test
    void checkForDuplicatesInHistory() {
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(task);
        Assertions.assertEquals(2, historyManager.getHistory().size());
    }

    @Test
    void checkHistoryElementMovesToLastOneAfterOneMoreCall() {
        historyManager.add(task);
        historyManager.add(epic);
        Assertions.assertEquals(task, historyManager.getHistory().getFirst());
        historyManager.add(task);
        Assertions.assertEquals(task, historyManager.getHistory().getLast());
        Assertions.assertNotEquals(task, historyManager.getHistory().getFirst());
    }

    @Test
    void checkTaskIsRemovedFromHistory() {
        historyManager.add(task);
        Assertions.assertEquals(1, historyManager.getHistory().size());
        historyManager.remove(task.getId());
        Assertions.assertEquals(0, historyManager.getHistory().size());
    }

    @Test
    void removeTaskFromTheMiddleOfHistory() {
        historyManager.add(task);
        historyManager.add(subTask);
        historyManager.add(epic);
        Assertions.assertEquals(3, historyManager.getHistory().size());
        historyManager.remove(epic.getId());
        Assertions.assertEquals(List.of(task, subTask), historyManager.getHistory());
    }

    @Test
    void removeTaskFromTheEndOfHistory() {
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subTask);
        Assertions.assertEquals(3, historyManager.getHistory().size());
        historyManager.remove(subTask.getId());
        Assertions.assertEquals(2, historyManager.getHistory().size());
        Assertions.assertEquals(epic, historyManager.getHistory().getLast());
        Assertions.assertFalse(historyManager.getHistory().contains(subTask));
    }

    @Test
    void removeFirstTaskFromHistory() {
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subTask);
        Assertions.assertEquals(3, historyManager.getHistory().size());
        historyManager.remove(task.getId());
        Assertions.assertEquals(2, historyManager.getHistory().size());
        Assertions.assertEquals(epic, historyManager.getHistory().getFirst());
    }
}