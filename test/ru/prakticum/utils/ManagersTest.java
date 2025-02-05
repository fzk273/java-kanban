package ru.prakticum.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ManagersTest {


    @Test
    void checkThatAllFieldsAreInitialised() {
        Assertions.assertNotNull(Managers.getDefault());
    }
}