package ru.prakticum.utils;

import ru.prakticum.interfaces.HistoryManager;
import ru.prakticum.interfaces.Manager;
import ru.prakticum.managers.InMemoryHistoryManager;
import ru.prakticum.managers.InMemoryTaskManager;


public final class Managers {

    public static Manager getDefault() {
        return new InMemoryTaskManager(getDefaultHistory());
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
