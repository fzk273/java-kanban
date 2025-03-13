package ru.prakticum.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.prakticum.adapters.DurationAdapter;
import ru.prakticum.adapters.LocalDateTimeAdapter;
import ru.prakticum.interfaces.TaskManager;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.utils.Managers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

//TODO практически уверен, что копипастить хендлер не лучшая идея. Как реализовать лучше?
public class EpicsHandler extends BaseHttpHandler implements HttpHandler {
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
                Epic epic = gson.fromJson(body, Epic.class);
                Epic newEpic = taskManager.createEpic(epic);
                super.sendCreated(httpExchange, newEpic.toJson());
                break;
            case "GET":
                if (splittedPath.length > 3 && splittedPath[3].equals("subtasks")) {
                    Integer id = Integer.parseInt(splittedPath[2]);
                    String epicSubtasks = taskManager.getEpicSubtasks(taskManager.getEpicById(id)).stream()
                            .map(SubTask::toJson)
                            .collect(Collectors.joining(","));
                    super.sendText(httpExchange, "{" + epicSubtasks + "}");
                } else if (splittedPath.length > 2) {
                    Integer id = Integer.parseInt(splittedPath[2]);
                    Epic epic1 = taskManager.getEpicById(id);
                    if (epic1 != null) {
                        super.sendText(httpExchange, epic1.toJson());
                    } else {
                        super.sendNotFound(httpExchange, "there is no subtask with id: " + id);
                    }
                } else {
                    String epicsJson = taskManager.getEpics().stream()
                            .map(Epic::toJson)
                            .collect(Collectors.joining(","));
                    super.sendText(httpExchange, "{" + epicsJson + "}");
                }
                break;
            case "DELETE":
                if (splittedPath.length > 2) {
                    Integer id = Integer.parseInt(splittedPath[2]);
                    taskManager.deleteEpicById(id);
                    super.sendText(httpExchange, "");
                }
                break;
            default:
                super.sendText(httpExchange, "ERROR! there is no method: " + method + " for this path");
        }

    }
}
