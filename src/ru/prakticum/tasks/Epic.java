package ru.prakticum.tasks;

import ru.prakticum.enums.TaskType;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds;
    private TaskType taskType;

    public Epic(String name, String description) {
        super(name, description);
        subtaskIds = new ArrayList<>();
        this.taskType = TaskType.EPIC;
    }

    @Override
    public TaskType getTaskType() {
        return taskType;
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void addSubtask(Integer subtaskId) {
        subtaskIds.add(subtaskId);
    }

    @Override
    public String toString() {
        return super.getId() + "," + taskType.toString()
                + "," + super.getName()
                + "," + super.getStatus()
                + "," + super.getDescription()
                + ",";
    }
}
