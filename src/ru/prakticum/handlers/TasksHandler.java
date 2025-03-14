package ru.prakticum.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.prakticum.interfaces.TaskManager;
import ru.prakticum.tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TasksHandler extends BaseHttpHandler implements HttpHandler {

    public TasksHandler(TaskManager taskManager) {
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
                Task task = gson.fromJson(body, Task.class);
                if (task.getId() == null) {
                    Task newTask = taskManager.createTask(task);
                    sendText(httpExchange, gson.toJson(newTask), 201);
                } else {
                    //TODO вообще не понимаю как реализоывать проверку на пересечение из этого класа. need help
//                    super.sendHasInteractions(httpExchange, "task is overlapping");
                    taskManager.updateTask(task);
                    sendText(httpExchange, gson.toJson(task), 201);
                }
                break;
            case "GET":
                if (splittedPath.length > 2) {
                    Integer id = Integer.parseInt(splittedPath[2]);
                    Task task1 = taskManager.getTaskById(id);
                    if (task1 != null) {
                        sendText(httpExchange, gson.toJson(task1), 200);
                    } else {
                        sendText(httpExchange, "there is no task with id: " + id, 404);
                    }
                } else {
                    String tasksJson = gson.toJson(taskManager.getTasks());
                    sendText(httpExchange, tasksJson, 200);
                }
                break;
            case "DELETE":
                if (splittedPath.length > 2) {
                    Integer id = Integer.parseInt(splittedPath[2]);
                    taskManager.deleteTaskByID(id);
                    sendText(httpExchange, "", 200);
                }
                break;
            default:
                sendText(httpExchange, "ERROR! method: " + method + " not allowed", 405);
        }

    }
}
