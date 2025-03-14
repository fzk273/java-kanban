package ru.prakticum.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.prakticum.interfaces.TaskManager;
import ru.prakticum.tasks.Epic;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class EpicsHandler extends BaseHttpHandler implements HttpHandler {
    public EpicsHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String path = httpExchange.getRequestURI().getPath();
        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        String[] splittedPath = path.split("/");
        switch (method) {
            case "POST":
                Epic epic = gson.fromJson(body, Epic.class);
                Epic newEpic = taskManager.createEpic(epic);
                sendText(httpExchange, gson.toJson(newEpic), 201);
                break;
            case "GET":
                if (splittedPath.length > 3 && splittedPath[3].equals("subtasks")) {
                    Integer id = Integer.parseInt(splittedPath[2]);
                    String epicSubtasks = gson.toJson(taskManager.getEpicSubtasks(taskManager.getEpicById(id)));
                    sendText(httpExchange, epicSubtasks, 200);
                } else if (splittedPath.length > 2) {
                    Integer id = Integer.parseInt(splittedPath[2]);
                    Epic epic1 = taskManager.getEpicById(id);
                    if (epic1 != null) {
                        sendText(httpExchange, gson.toJson(epic1), 200);
                    } else {
                        sendText(httpExchange, "there is no subtask with id: " + id, 404);
                    }
                } else {
                    String epicsJson = gson.toJson(taskManager.getEpics());
                    sendText(httpExchange, epicsJson, 200);
                }
                break;
            case "DELETE":
                if (splittedPath.length > 2) {
                    Integer id = Integer.parseInt(splittedPath[2]);
                    taskManager.deleteEpicById(id);
                    sendText(httpExchange, "", 200);
                }
                break;
            default:
                sendText(httpExchange, "ERROR! there is no method: " + method + " for this path", 405);
        }

    }
}
