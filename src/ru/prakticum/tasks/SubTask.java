package ru.prakticum.tasks;

import ru.prakticum.enums.TaskType;
import ru.prakticum.utils.CSVTaskFormat;

public class SubTask extends Task {
    private Integer epicId;

    public SubTask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.SUBTASK;
    }

    public Integer getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return super.getId() + "," + getTaskType()
                + "," + super.getName()
                + "," + super.getStatus()
                + "," + super.getDescription()
                + "," + getStartTime().format(CSVTaskFormat.getDateTimeFormatter())
                + "," + super.getDuration().toMinutes()
                + "," + getEpicId();
    }
}
