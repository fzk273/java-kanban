package ru.prakticum.interfaces;

import ru.prakticum.tasks.Task;

import java.util.List;

public interface HistoryManager {

    List<Task> getHistory();

    void add(Task task);
}
