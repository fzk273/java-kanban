package ru.prakticum.utils;

import ru.prakticum.interfaces.HistoryManager;
import ru.prakticum.interfaces.TaskManager;
import ru.prakticum.managers.FileBackedTaskManager;
import ru.prakticum.managers.InMemoryHistoryManager;
import ru.prakticum.managers.InMemoryTaskManager;

import java.io.File;
import java.nio.file.Paths;


public final class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getFileBackedTaskManager() {
        File backupFile = new File(Paths.get("src/ru/prakticum/resources/save_example.csv").toString());
        return FileBackedTaskManager.loadFromFile(backupFile);
    }

}
