package ru.prakticum.tasks;

import ru.prakticum.enums.TaskType;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds;

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

    @Override
    public String toString() {
        return super.getId() + "," + getTaskType()
                + "," + super.getName()
                + "," + super.getStatus()
                + "," + super.getDescription()
                + ",";
    }
}
