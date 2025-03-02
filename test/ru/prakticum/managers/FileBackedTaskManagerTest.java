package ru.prakticum.managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {
    private File tempFile;

    @BeforeEach
    public void init() throws IOException {
        tempFile = File.createTempFile("tempfile", null);
        taskManager = FileBackedTaskManager.loadFromFile(tempFile);
        super.init();
    }

    @AfterEach
    public void cleanup() {
        tempFile.delete();
    }

    @Test
    public void checkDataConsistency() {
        taskManager.createEpic(epic);
        taskManager.createTask(task);
        taskManager.createSubtask(subTask);
        FileBackedTaskManager testFbManager = FileBackedTaskManager.loadFromFile(tempFile);
        Assertions.assertEquals(testFbManager.getTasks(), taskManager.getTasks());
        Assertions.assertEquals(testFbManager.getSubtasks(), taskManager.getSubtasks());
        Assertions.assertEquals(testFbManager.getEpics(), taskManager.getEpics());
        Assertions.assertEquals(2, testFbManager.counter);
    }


}

