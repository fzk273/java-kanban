package ru.prakticum.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileBackedTaskManagerTest {
    private File tempFile;
    private FileBackedTaskManager fbManager;
    private Task task;
    private Epic epic;
    private SubTask subTask;
    private FileReader fileReader;

    @BeforeEach
    public void init() {
        task = new Task("Task name", "Task name");
        epic = new Epic("Epic name", "Epic desc");
        subTask = new SubTask("SubTask name", "SubTaskDesc", 2);
        try {
            tempFile = File.createTempFile("tempfile", null);
            FileWriter fw = new FileWriter(tempFile);
            String basicFileContent = "id,type,name,status,description,epic\n" + "1,TASK,Task1,NEW,Description task1,\n"
                    + "2,EPIC,Epic2,DONE,Description epic2,\n" + "3,SUBTASK,Sub Task2,DONE,Description sub task3,2";
            fw.write(basicFileContent);
            fw.flush();
            fbManager = FileBackedTaskManager.loadFromFile(tempFile);
        } catch (IOException e) {
            throw new RuntimeException("cannot create a file");
        }
    }

    @Test
    public void loadTasksFromFile() {
        Assertions.assertEquals(1, fbManager.getTasks().size());
        Assertions.assertEquals(1, fbManager.getEpics().size());
        Assertions.assertEquals(1, fbManager.getSubtasks().size());
    }

    @Test
    public void saveTasksToFile() {
        fbManager.createTask(task);
        fbManager.createEpic(epic);
        fbManager.createSubtask(subTask);
        try {
            List<String> allLines = Files.readAllLines(Paths.get(tempFile.toURI()));
            Assertions.assertTrue(allLines.contains("4,TASK,Task name,NEW,Task name,"));
            Assertions.assertTrue(allLines.contains("5,EPIC,Epic name,NEW,Epic desc,"));
            Assertions.assertTrue(allLines.contains("6,SUBTASK,SubTask name,NEW,SubTaskDesc,2"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void deleteTasksFromFile() {
        try {
            fbManager.deleteTaskByID(1);
            List<String> allLines = Files.readAllLines(Paths.get(tempFile.toURI()));
            Assertions.assertFalse(allLines.contains("1,TASK,Task1,NEW,Description task1,"));
            fbManager.deleteSubtaskById(3);
            allLines = Files.readAllLines(Paths.get(tempFile.toURI()));
            Assertions.assertFalse(allLines.contains("3,SUBTASK,Sub Task2,DONE,Description sub task3,2"));
            fbManager.deleteEpicById(2);
            allLines = Files.readAllLines(Paths.get(tempFile.toURI()));
            Assertions.assertFalse(allLines.contains("2,EPIC,Epic2,DONE,Description epic2,"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void setCounterAfterTasksUpload() {
        Assertions.assertEquals(4, fbManager.getCounter());
    }


}

