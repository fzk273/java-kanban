package ru.prakticum.handlers;

import com.sun.net.httpserver.HttpExchange;
import ru.prakticum.interfaces.TaskManager;

import java.io.IOException;


public class PriorityHandler extends BaseHttpHandler {
    public PriorityHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if (method.equals("GET")) {
            String prioritizedTasksJson = gson.toJson(taskManager.getPrioritizedTasks());
            sendText(httpExchange, prioritizedTasksJson, 200);
        } else {
            sendText(httpExchange, "ERROR! there is no method: " + method + " for this path", 405);
        }
    }
}

