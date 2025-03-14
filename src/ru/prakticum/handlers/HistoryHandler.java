package ru.prakticum.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.prakticum.interfaces.TaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    public HistoryHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if (method.equals("GET")) {
            String historyJson = gson.toJson(taskManager.getHistory());
            super.sendText(httpExchange, historyJson, 200);
        } else {
            super.sendText(httpExchange, "ERROR! there is no method: " + method + " for this path", 405);
        }
    }
}
