package ru.prakticum;

public class SubTask extends Task{
    private Integer epicId;

    public SubTask(Integer id, String name, String description, Integer epicId) {
        super(id, name, description);
        this.epicId = epicId;
    }
}
