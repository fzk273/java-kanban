package ru.prakticum.managers;

import ru.prakticum.enums.TaskType;
import ru.prakticum.exceptions.ManagerSaveException;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;
import ru.prakticum.utils.CSVTaskFormat;

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
                Task task = CSVTaskFormat.taskFromString(List.of(line.split(",")));
                fileBackedTaskManager.addTask(task);
                if (taskIdCounter <= task.getId()) {
                    taskIdCounter++;
                    fileBackedTaskManager.counter = taskIdCounter;
                }
                line = reader.readLine();
            }

        } catch (IOException e) {
            throw new ManagerSaveException("failed to load a history file");
        }

        return fileBackedTaskManager;
    }

    private  void addTask(Task task) {
        if (task.getTaskType().equals(TaskType.TASK)) {
            tasks.put(task.getId(), task);
        }
        if (task.getTaskType().equals(TaskType.EPIC)) {
            epics.put(task.getId(), (Epic) task);
        }
        if (task.getTaskType().equals(TaskType.SUBTASK)) {
            SubTask subTask = (SubTask) task;
            subtasks.put(task.getId(), subTask);
            epics.get(subTask.getEpicId()).addSubtask(subTask.getId());
        }
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


    private void save() {
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
