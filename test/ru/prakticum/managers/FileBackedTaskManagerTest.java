package ru.prakticum.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class FileBackedTaskManagerTest {
    private File tempFile;
    private FileBackedTaskManager fbManager;
    private Task task;
    private Epic epic;
    private SubTask subTask;
    private LocalDateTime nowDateTime;
    private Duration oneHour;

    @BeforeEach
    public void init() {
        nowDateTime = LocalDateTime.now();
        oneHour = Duration.ofHours(1);
        task = new Task("Task name", "Task name");
        task.setDuration(oneHour);
        task.setStartTime(nowDateTime);
        epic = new Epic("Epic name", "Epic desc");
        epic.setDuration(oneHour);
        epic.setStartTime(nowDateTime);
        subTask = new SubTask("SubTask name", "SubTaskDesc", 1);
        subTask.setDuration(oneHour);
        subTask.setStartTime(nowDateTime.plus(Duration.ofHours(2)));
        try {
            tempFile = File.createTempFile("tempfile", null);
            FileWriter fw = new FileWriter(tempFile);
            String basicFileContent = "id,type,name,status,description,epic\n";
            fw.write(basicFileContent);
            fw.flush();
            fbManager = FileBackedTaskManager.loadFromFile(tempFile);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException("cannot create a file");
        }
    }


    @Test
    public void checkDataConsistency() {
        fbManager.createTask(task);
        fbManager.createEpic(epic);
        fbManager.createSubtask(subTask);
        FileBackedTaskManager testFbManager = FileBackedTaskManager.loadFromFile(tempFile);
        Assertions.assertEquals(testFbManager.getTasks(), fbManager.getTasks());
        Assertions.assertEquals(testFbManager.getSubtasks(), fbManager.getSubtasks());
        Assertions.assertEquals(testFbManager.getEpics(), fbManager.getEpics());
        Assertions.assertEquals(3, testFbManager.counter);
    }


}

