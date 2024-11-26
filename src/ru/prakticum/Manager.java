package ru.prakticum;

import java.util.HashMap;

public class Manager {
    private Integer counter;
    private HashMap tasks;
    private HashMap subtasks;
    private HashMap epics;

    public Manager(){
        counter = 0;
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
    }


}
