package ru.prakticum.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.prakticum.adapters.DurationAdapter;
import ru.prakticum.adapters.LocalDateTimeAdapter;
import ru.prakticum.interfaces.TaskManager;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;
import ru.prakticum.utils.Managers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

//TODO практически уверен, что копипастить хендлер не лучшая идея. Как реализовать лучше?
public class SubtasksHandler extends BaseHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String path = httpExchange.getRequestURI().getPath();
        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        String[] splittedPath = path.split("/");
        TaskManager taskManager = Managers.getFileBackedTaskManager();
        switch (method) {
            case "POST":
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                        .registerTypeAdapter(Duration.class, new DurationAdapter()).create();
                SubTask subTask = gson.fromJson(body, SubTask.class);

                if (subTask.getId() != null) {
                    //TODO вообще не понимаю как реализоывать проверку на пересечение из этого класа. need help
//                    super.sendHasInteractions(httpExchange, "subtask is overlapping");
                    taskManager.updateTask(subTask);
                } else {
                    Task newTask = taskManager.createSubtask(subTask);
                    super.sendText(httpExchange, newTask.toJson());
                }
                break;
            case "GET":
                if (splittedPath.length > 2) {
                    Integer id = Integer.parseInt(splittedPath[2]);
                    SubTask subTask1 = taskManager.getSubtaskById(id);
                    if (subTask1 != null) {
                        super.sendText(httpExchange, subTask1.toJson());
                    } else {
                        super.sendNotFound(httpExchange, "there is no subtask with id: " + id);
                    }
                } else {
                    String subtasksJson = taskManager.getSubtasks().stream()
                            .map(Task::toJson)
                            .collect(Collectors.joining(","));
                    super.sendText(httpExchange, "{" + subtasksJson + "}");
                }
                break;
            case "DELETE":
                if (splittedPath.length > 2) {
                    Integer id = Integer.parseInt(splittedPath[2]);
                    taskManager.deleteSubtaskById(id);
                    super.sendText(httpExchange, "");
                }
                break;
            default:
                super.sendText(httpExchange, "ERROR! there is no method: " + method + " for this path");
        }

    }
}
