package ru.prakticum.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.prakticum.interfaces.TaskManager;
import ru.prakticum.tasks.SubTask;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SubtasksHandler extends BaseHttpHandler implements HttpHandler {
    public SubtasksHandler(TaskManager taskManager) {
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
                SubTask subTask = gson.fromJson(body, SubTask.class);
                if (subTask.getId() == null) {
                    //TODO вообще не понимаю как реализоывать проверку на пересечение из этого класа. need help
//                    sendHasInteractions(httpExchange, "subtask is overlapping");
                    SubTask subTask1 = taskManager.createSubtask(subTask);
                    sendText(httpExchange, gson.toJson(subTask1), 201);
                } else {
                    taskManager.updateSubtask(subTask);
                    sendText(httpExchange, gson.toJson(subTask), 201);
                }
                break;
            case "GET":
                if (splittedPath.length > 2) {
                    Integer id = Integer.parseInt(splittedPath[2]);
                    SubTask subTask1 = taskManager.getSubtaskById(id);
                    if (subTask1 != null) {
                        sendText(httpExchange, gson.toJson(subTask1), 200);
                    } else {
                        sendText(httpExchange, "there is no subtask with id: " + id, 404);
                    }
                } else {
                    String subtasksJson = gson.toJson(taskManager.getSubtasks());
                    sendText(httpExchange, subtasksJson, 200);
                }
                break;
            case "DELETE":
                if (splittedPath.length > 2) {
                    Integer id = Integer.parseInt(splittedPath[2]);
                    taskManager.deleteSubtaskById(id);
                    sendText(httpExchange, "", 200);
                }
                break;
            default:
                sendText(httpExchange, "ERROR! method: " + method + " not allowed", 405);
        }
    }
}
