package ru.prakticum.managers;

import ru.prakticum.interfaces.TaskManager;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager; //параметризованное поле taskManager, которое мы можем использовать в наследниках и инициализировать его конкретной реализацией таск менеджера

    /**
     * Реализация тестов всех методов интерфейса `TaskManager` должна быть в этом классе, классы-наследники уже будут их выполнять с конкретной реализацией менеджера
     */

}