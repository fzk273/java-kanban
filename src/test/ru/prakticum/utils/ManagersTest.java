package ru.prakticum.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.prakticum.interfaces.TaskManager;
import ru.prakticum.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {


    @Test
    void checkThatAllFieldsAreInitialised() {
        assertNotNull(Managers.getDefault());
    }
}