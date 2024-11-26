package ru.prakticum;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList subtaskIds;

    public Epic(Integer id, String name, String description) {
        super(id, name, description);
        subtaskIds = new ArrayList<Integer>();
    }
}
