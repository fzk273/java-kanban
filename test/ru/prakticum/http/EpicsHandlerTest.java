package ru.prakticum.http;

import org.junit.jupiter.api.*;
import ru.prakticum.tasks.SubTask;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class EpicsHandlerTest extends InitHandlers {
    private String epicJson;

    @BeforeAll
    public static void initServer() throws IOException {
        InitHandlers.initServer();
    }

    @BeforeEach
    public void init() {
        super.init();

    }

    @AfterAll
    public static void terminate() {
        InitHandlers.terminate();
    }

    @Test
    public void epicGetByIdIs200() throws IOException, InterruptedException {
        epic.setDescription("desc");
        taskManager.createEpic(epic);
        Integer epicId = taskManager.getEpics().getFirst().getId();
        request = HttpRequest.newBuilder(uri.resolve("./epics/" + epicId)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
        //TODO тут постоянно возникает java.io.IOException: HTTP/1.1 header parser received no bytes не понимаю, что не так
        // если только этот тест отдельно запустить, всё норм
    }

    @Test
    public void nonExistingEpicGetByIdIs404() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder(uri.resolve("./epics/12312")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(404, response.statusCode());
    }

    @Test
    public void epicCreationIs201() throws IOException, InterruptedException {
        epic.setDescription("new desc");
        epicJson = gson.toJson(epic);
        request = HttpRequest.newBuilder(uri.resolve("./epics"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(epicJson, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(201, response.statusCode());
    }

    @Test
    public void epicUpdateIs201() throws IOException, InterruptedException {
        epic.setDescription("new desc");
        epicJson = gson.toJson(epic);
        request = HttpRequest.newBuilder(uri.resolve("./epics"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(epicJson, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(201, response.statusCode());
    }

    @Test
    public void epicDeletionIs200() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder(uri.resolve("./epics/1"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());

    }

    @Test
    public void epicSubtasksIs200() throws IOException, InterruptedException {
        taskManager.createEpic(epic);
        Integer epicId = taskManager.getEpics().getFirst().getId();
        SubTask newSubtask = new SubTask("name", "desc", epicId);
        taskManager.createSubtask(newSubtask);
        request = HttpRequest.newBuilder(uri.resolve("./epics/" + epicId + "/subtasks")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void nonExistingEpicSubtasksIs404() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder(uri.resolve("./epics/100/subtasks")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(404, response.statusCode());
    }
}
