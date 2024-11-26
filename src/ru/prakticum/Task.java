package ru.prakticum;


import ru.prakticum.enums.Status;

public class Task {
    private Integer id;
    private Status status;
    private String name;
    private String description;

    public Task(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description=description;
        this.status = Status.NEW;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
