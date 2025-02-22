package ru.prakticum.tasks;

import ru.prakticum.enums.TaskType;
import ru.prakticum.utils.CSVTaskFormat;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds;
    //TODO не понимаю как правильно проинициализировать класс без null и без того, что бы убрать duration
    private LocalDateTime endTime = null;
    private Duration duration;
    private LocalDateTime startTime;

    public Epic(String name, String description, LocalDateTime startTime, Duration duration) {
        super(name, description, startTime, duration);
        subtaskIds = new ArrayList<>();
        this.startTime = startTime;
        this.duration = duration;

    }

    @Override
    public TaskType getTaskType() {
        return TaskType.EPIC;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public Duration getDuration() {
        return duration;
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

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
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
