package ru.prakticum.tasks;


import ru.prakticum.enums.Status;
import ru.prakticum.enums.TaskType;

import java.util.Objects;

public class Task {
    private Integer id;
    private Status status;
    private String name;
    private String description;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    public TaskType getTaskType() {
        return TaskType.TASK;
    }

    public Integer getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return id + "," + getTaskType()
                + "," + name
                + "," + status
                + "," + description
                + ",";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return Objects.equals(id, task.id) && status == task.status && Objects.equals(name, task.name) && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, name, description);
    }
}
