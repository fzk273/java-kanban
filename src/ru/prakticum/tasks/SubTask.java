package ru.prakticum.tasks;

public class SubTask extends Task {
    private Integer epicId;

    public SubTask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + super.getId() +
                ", status=" + super.getStatus() +
                ", name='" + super.getName() + '\'' +
                ", description='" + super.getDescription() + '\'' +
                "epicId=" + epicId +
                "} ";
    }
}
