package ru.prakticum;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds;

    public Epic(Integer id, String name, String description) {
        super(id, name, description);
        subtaskIds = new ArrayList<Integer>();
    }


    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + super.getId() +
                ", status=" + super.getStatus() +
                ", name='" + super.getName() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                "subtaskIds=" + subtaskIds +
                "}";
    }
}
