package ru.prakticum.utils;

import ru.prakticum.interfaces.HistoryManager;
import ru.prakticum.interfaces.TaskManager;
import ru.prakticum.managers.InMemoryHistoryManager;
import ru.prakticum.managers.InMemoryTaskManager;


public final class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
