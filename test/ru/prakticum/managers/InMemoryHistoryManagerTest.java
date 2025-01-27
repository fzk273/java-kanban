package ru.prakticum.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.prakticum.interfaces.HistoryManager;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;
import ru.prakticum.utils.Managers;

import static ru.prakticum.managers.InMemoryHistoryManager.*;

class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;
    private Task task;
    private Epic epic;
    private SubTask subTask;

    @BeforeEach
    void init() {
        historyManager = Managers.getDefaultHistory();
        task = new Task("task", "desc");
        task.setId(0);
        epic = new Epic("epic", "desc");
        epic.setId(1);
        subTask = new SubTask("subtask", "desc", 1);
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
        Task lastTask = new Task("last task", "desc");
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
        Assertions.assertEquals(task, historyManager.getHistory().get(0));
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
    //TODO непонимаю как протестить класс Node не могу никак импортнуть его сюда, поскольку он находится в другом классе. нид хэлп

}