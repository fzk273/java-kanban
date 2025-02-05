package ru.prakticum.managers;

import ru.prakticum.enums.Status;
import ru.prakticum.enums.TaskType;
import ru.prakticum.exceptions.ManagerSaveException;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;

import java.io.*;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File backUpFile;

    public FileBackedTaskManager(File backUpFile) {
        super();
        this.backUpFile = backUpFile;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int taskIdCounter = 0;
            String line = reader.readLine();
            //skipping headline of csv
            line = reader.readLine();
            while (line != null) {
                List<String> splittedLine = List.of(line.split(","));
                int taskId = Integer.parseInt(splittedLine.get(0));
                TaskType taskType = TaskType.valueOf(splittedLine.get(1));
                String taskName = splittedLine.get(2);
                Status taskStatus = Status.valueOf(splittedLine.get(3));
                String taskDescription = splittedLine.get(4);
                //TODO я практически уверен, что всё это можно реализовать сильно лаконичнее. need help
                if (taskType.equals(TaskType.TASK)) {
                    Task taskFromBackUP = new Task(taskName, taskDescription);
                    taskFromBackUP.setId(taskId);
                    taskFromBackUP.setStatus(taskStatus);
                    fileBackedTaskManager.tasks.put(taskId, taskFromBackUP);
                }
                if (taskType.equals(TaskType.EPIC)) {
                    Epic epicFromBackUP = new Epic(taskName, taskDescription);
                    epicFromBackUP.setId(taskId);
                    epicFromBackUP.setStatus(taskStatus);
                    fileBackedTaskManager.epics.put(taskId, epicFromBackUP);
                }
                if (taskType.equals(TaskType.SUBTASK)) {
                    SubTask subtaskFromBackUP = new SubTask(taskName, taskDescription, Integer.valueOf(splittedLine.get(5)));
                    subtaskFromBackUP.setId(taskId);
                    subtaskFromBackUP.setStatus(taskStatus);
                    fileBackedTaskManager.subtasks.put(taskId, subtaskFromBackUP);
                }
                if (taskIdCounter < taskId) {
                    fileBackedTaskManager.counter = taskId + 1;
                }
                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileBackedTaskManager;
    }

    @Override
    public Task createTask(Task task) {
        Task newTask = super.createTask(task);
        save();
        return newTask;
    }

    @Override
    public SubTask createSubtask(SubTask subTask) {
        SubTask newSubtask = super.createSubtask(subTask);
        save();
        return newSubtask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        Epic newEpic = super.createEpic(epic);
        save();
        return newEpic;
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void deleteTaskByID(Integer id) {
        super.deleteTaskByID(id);
        save();
    }

    @Override
    public void deleteSubtaskById(Integer id) {
        super.deleteSubtaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(Integer id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(SubTask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }


    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(backUpFile.getAbsolutePath()))) {
            writer.write("id,type,name,status,description,epic\n");
            for (Task task : getTasks()) {
                writer.write(task.toString() + "\n");
            }
            for (Epic epic : getEpics()) {
                writer.write(epic.toString() + "\n");
            }
            for (SubTask subTask : getSubtasks()) {
                writer.write(subTask.toString() + "\n");
            }

        } catch (IOException e) {
            throw new ManagerSaveException("failed to save history to the a file");
        }
    }

}
