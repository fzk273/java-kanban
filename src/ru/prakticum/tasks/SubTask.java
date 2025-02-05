package ru.prakticum.tasks;

import ru.prakticum.enums.TaskType;

public class SubTask extends Task {
    private Integer epicId;
    private TaskType taskType;

    public SubTask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId = epicId;
        this.taskType = TaskType.SUBTASK;
    }

    @Override
    public TaskType getTaskType() {
        return taskType;
    }

    public Integer getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return super.getId() + "," + taskType.toString()
                + "," + super.getName()
                + "," + super.getStatus()
                + "," + super.getDescription()
                + "," + getEpicId();
    }
}
