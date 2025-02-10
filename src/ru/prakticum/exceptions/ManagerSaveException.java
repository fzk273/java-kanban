package ru.prakticum.exceptions;

public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(String e) {
        super(e);
    }
}
