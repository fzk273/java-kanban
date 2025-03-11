package ru.prakticum.tasks;

import ru.prakticum.enums.TaskType;
import ru.prakticum.utils.CSVTaskFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds;
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
        subtaskIds = new ArrayList<>();
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.EPIC;
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void addSubtask(Integer subtaskId) {
        subtaskIds.add(subtaskId);
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return super.getId() + "," + getTaskType()
                + "," + super.getName()
                + "," + super.getStatus()
                + "," + super.getDescription()
                + "," + super.getStartTime().format(CSVTaskFormat.getDateTimeFormatter())
                + "," + super.getDuration().toMinutes()
                + ",";
    }
}
