package ru.prakticum.exceptions;

public class TaskOverlappingException extends IllegalArgumentException {
    public TaskOverlappingException(String e) {
        super(e);
    }
}
