package ru.prakticum.http;

import org.junit.jupiter.api.*;
import ru.prakticum.tasks.Task;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

public class TasksHandlerTest extends InitHandlers {

    @Test
    public void taskGetByIdIs200() throws IOException, InterruptedException {
        task.setDescription("desc");
        taskManager.createTask(task);
        Integer taskId = taskManager.getTasks().getFirst().getId();
        request = HttpRequest.newBuilder(uri.resolve("./tasks/" + taskId)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void nonExistingTaskGetByIdIs404() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder(uri.resolve("./tasks/12312")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(404, response.statusCode());
    }

    @Test
    public void taskCreationIs201() throws IOException, InterruptedException {
        Task newTask = new Task("new Name", "new Desc");
        String newTaskJson = gson.toJson(newTask);
        Assertions.assertEquals(0, taskManager.getTasks().size());

        request = HttpRequest.newBuilder(uri.resolve("./tasks"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(newTaskJson, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals(1, taskManager.getTasks().size());
    }

    @Test
    public void taskUpdateIs201() throws IOException, InterruptedException {
        taskManager.createTask(task);
        Assertions.assertEquals(1, taskManager.getTasks().size());
        Task task1 = taskManager.getTaskById(task.getId());
        task1.setDescription("my new desc");
        String task1Json = gson.toJson(task1);
        request = HttpRequest.newBuilder(uri.resolve("./tasks"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(task1Json, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals(1, taskManager.getTasks().size());


    }

    @Test
    public void taskOverlappingIs406() throws IOException, InterruptedException {
        Task newTask = new Task("new Name", "new Desc");
        newTask.setStartTime(LocalDateTime.now());
        newTask.setDuration(Duration.ofHours(2));
        String newTaskJson = gson.toJson(newTask);
        request = HttpRequest.newBuilder(uri.resolve("./tasks"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(newTaskJson, StandardCharsets.UTF_8))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(406, response.statusCode());
    }

    @Test
    public void taskDeletionIs200() throws IOException, InterruptedException {
        taskManager.createTask(task);
        Assertions.assertEquals(1, taskManager.getTasks().size());
        request = HttpRequest.newBuilder(uri.resolve("./tasks/" + task.getId()))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(0, taskManager.getTasks().size());


    }
}
