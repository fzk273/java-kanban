package ru.prakticum.utils;

import ru.prakticum.enums.Status;
import ru.prakticum.enums.TaskType;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CSVTaskFormat {
    public static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyy HH:mm");
    }

    public static Task taskFromString(List<String> splittedLine) {
        int taskId = Integer.parseInt(splittedLine.get(0));
        TaskType taskType = TaskType.valueOf(splittedLine.get(1));
        String taskName = splittedLine.get(2);
        Status taskStatus = Status.valueOf(splittedLine.get(3));
        String taskDescription = splittedLine.get(4);
        LocalDateTime taskStartTime = LocalDateTime.parse(splittedLine.get(5), getDateTimeFormatter());
        Duration taskDuration = Duration.ofMinutes(Long.parseLong(splittedLine.get(6)));
        Task task = null;
        if (taskType.equals(TaskType.TASK)) {
            task = new Task(taskName, taskDescription);
            task.setId(taskId);
            task.setStartTime(taskStartTime);
            task.setDuration(taskDuration);
            task.setStatus(taskStatus);
        }
        if (taskType.equals(TaskType.EPIC)) {
            task = new Epic(taskName, taskDescription);
            task.setId(taskId);
            task.setStartTime(taskStartTime);
            task.setDuration(taskDuration);
            task.setStatus(taskStatus);
        }
        if (taskType.equals(TaskType.SUBTASK)) {
            task = new SubTask(taskName, taskDescription, Integer.valueOf(splittedLine.get(7)));
            task.setId(taskId);
            task.setStartTime(taskStartTime);
            task.setDuration(taskDuration);
            task.setStatus(taskStatus);
        }
        return task;
    }
}
