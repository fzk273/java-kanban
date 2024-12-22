package ru.prakticum.managers;

import ru.prakticum.interfaces.HistoryManager;
import ru.prakticum.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history;
    private final int historySize = 10;

    public InMemoryHistoryManager() {
        history = new ArrayList<>();
    }


    @Override
    public List<Task> getHistory() {
        return history;
    }

    boolean isHistoryFull() {
        return history.size() >= historySize;
    }

    @Override
    public void add(Task task) {
        if (isHistoryFull()) {
            history.removeFirst();
            history.add(task);
        } else {
            history.add(task);
        }
    }
}
