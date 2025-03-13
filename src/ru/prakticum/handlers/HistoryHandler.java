package ru.prakticum.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.prakticum.interfaces.TaskManager;
import ru.prakticum.tasks.Task;
import ru.prakticum.utils.Managers;

import java.io.IOException;
import java.util.stream.Collectors;

//TODO не понимаю как забирать один и тот же инстанс TaskManager во всех хендлерах, поэтому история невалидна
// нужна помощь.
public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        TaskManager taskManager = Managers.getFileBackedTaskManager();
        switch (method) {
            case "GET":
                String historyJson = taskManager.getHistory().stream()
                        .map(Task::toJson).collect(Collectors.joining(","));
                super.sendText(httpExchange, "{" + historyJson + "}");
                break;
            default:
                super.sendText(httpExchange, "ERROR! there is no method: " + method + " for this path");
        }
    }
}
