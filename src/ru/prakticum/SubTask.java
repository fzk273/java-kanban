package ru.prakticum;

public class SubTask extends Task {
    private Integer epicId;

    public SubTask(Integer id, String name, String description, Integer epicId) {
        super(id, name, description);
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
