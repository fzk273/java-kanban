package ru.prakticum.handlers;

import com.sun.net.httpserver.HttpExchange;
import ru.prakticum.interfaces.TaskManager;
import ru.prakticum.tasks.Epic;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class EpicsHandler extends BaseHttpHandler {
    public EpicsHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String path = httpExchange.getRequestURI().getPath();
        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        String[] splitPath = path.split("/");
        switch (method) {
            case "POST":
                Epic epic = gson.fromJson(body, Epic.class);
                Epic newEpic = taskManager.createEpic(epic);
                sendText(httpExchange, gson.toJson(newEpic), 201);
                break;
            case "GET":
                getMethod(httpExchange, splitPath);
                break;
            case "DELETE":
                if (splitPath.length > 2) {
                    Integer id = Integer.parseInt(splitPath[2]);
                    taskManager.deleteEpicById(id);
                    sendText(httpExchange, "", 200);
                }
                break;
            default:
                sendText(httpExchange, "ERROR! there is no method: " + method + " for this path", 405);
        }

    }

    public void getMethod(HttpExchange httpExchange, String[] splitPath) throws IOException {
        Integer epicId = null;
        boolean isSubtasksInPath = false;
        if (splitPath.length > 2) {
            epicId = Integer.parseInt(splitPath[2]);
            if (splitPath.length > 3) {
                isSubtasksInPath = splitPath[3].equals("subtasks");
            }
        }

        if (splitPath.length == 2) {
            String epicsJson = gson.toJson(taskManager.getEpics());
            sendText(httpExchange, epicsJson, 200);
        } else if (splitPath.length == 3) {
            Epic epic1 = taskManager.getEpicById(epicId);
            if (epic1 != null) {
                sendText(httpExchange, gson.toJson(epic1), 200);
            } else {
                sendText(httpExchange, "there is no epic with id: " + epicId, 404);
            }
        } else if (splitPath.length == 4 && isSubtasksInPath) {
            if (taskManager.getEpicById(epicId) != null) {
                String epicSubtasks = gson.toJson(taskManager.getEpicSubtasks(taskManager.getEpicById(epicId)));
                sendText(httpExchange, epicSubtasks, 200);
            } else {
                sendText(httpExchange, "there is epic with id: " + epicId, 404);
            }
        }

    }
}
