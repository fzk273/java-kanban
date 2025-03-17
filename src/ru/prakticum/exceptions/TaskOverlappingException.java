package ru.prakticum.exceptions;

public class TaskOverlappingException extends RuntimeException {
    public TaskOverlappingException(String e) {
        super(e);
    }
}
