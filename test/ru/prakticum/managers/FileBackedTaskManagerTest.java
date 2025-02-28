package ru.prakticum.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileBackedTaskManagerTest extends TaskManagerTest {
    private File tempFile;

    @BeforeEach
    public void init() {
        try {
            tempFile = File.createTempFile("tempfile", null);
            FileWriter fw = new FileWriter(tempFile);
            String basicFileContent = "id,type,name,status,description,epic\n";
            fw.write(basicFileContent);
            fw.flush();
            taskManager = FileBackedTaskManager.loadFromFile(tempFile);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException("cannot create a file");
        }
        super.init();
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

