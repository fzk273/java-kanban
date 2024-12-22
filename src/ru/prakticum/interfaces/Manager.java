package ru.prakticum.interfaces;

import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface Manager {
    Task createTask(Task task);

    SubTask createSubtask(SubTask subTask);

    Epic createEpic(Epic epic);

    ArrayList<Task> getTasks();

    ArrayList<SubTask> getSubtasks();

    ArrayList<Epic> getEpics();

    Task getTaskById(Integer id);

    SubTask getSubtaskById(Integer id);

    Epic getEpicById(Integer id);

    boolean deleteTasks();

    boolean deleteSubtasks();

    boolean deleteEpics();

    void deleteTaskByID(Integer id);

    void deleteSubtaskById(Integer id);

    void deleteEpicById(Integer id);

    void updateTask(Task task);

    void updateSubtask(SubTask subtask);

    void updateEpic(Epic epic);

    ArrayList<SubTask> getEpicSubtasks(Epic epic);

    void updateEpicStatus(Epic epic);

    List<Task> getHistory();
}
