package ru.prakticum.http;

import org.junit.jupiter.api.*;
import ru.prakticum.tasks.Task;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class TasksHandlerTest extends InitHandlers {
    private Task task1;
    private String task1Json;

    @BeforeAll
    public static void initServer() throws IOException {
        InitHandlers.initServer();
    }

    @BeforeEach
    public void init() {
        super.init();
        task1 = new Task("task1", "desc1");
        task1.setStartTime(LocalDateTime.now().plusHours(8));
        task1Json = gson.toJson(task1);


    }

    @AfterAll
    public static void terminate() {
        InitHandlers.terminate();
    }

    @Test
    public void taskGetByIdIs200() throws IOException, InterruptedException {
        task.setDescription("desc");
        taskManager.createTask(task);
        Integer taskId = taskManager.getTasks().getFirst().getId();
        request = HttpRequest.newBuilder(uri.resolve("./tasks/"+ taskId)).GET().build();
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
        request = HttpRequest.newBuilder(uri.resolve("./tasks"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(task1Json, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(201, response.statusCode());
        //TODO тут постоянно возникает java.io.IOException: HTTP/1.1 header parser received no bytes не понимаю, что не так
        // если только этот тест отдельно запустить, всё норм
    }

    @Test
    public void taskUpdateIs201() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder(uri.resolve("./tasks"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(taskJson, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(201, response.statusCode());
    }

    @Test
    public void taskOverlappingIs406() {
        //TODO не понимаю как имлементировать, поскольку не получаю 406 в создании таски
    }

    @Test
    public void taskDeletionIs200() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder(uri.resolve("./tasks/1"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());

    }
}
