package ru.prakticum.http;

import org.junit.jupiter.api.*;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

public class SubTasksHandlerTest extends InitHandlers {
    private SubTask subTask1;
    private String subTask1Json;

    @BeforeEach
    public void init() {
        super.init();
        taskManager.createEpic(epic);
        Integer epicId = taskManager.getEpics().getFirst().getId();
        subTask1 = new SubTask("subtask1", "subdesc1", epicId);
        subTask1.setDuration(Duration.ofHours(5));
        subTask1.setStartTime(LocalDateTime.now().plusHours(2));
        subTask1Json = gson.toJson(subTask1);
    }

    @Test
    public void subTaskGetByIdIs200() throws IOException, InterruptedException {
        epic.setDescription("desc");
        taskManager.createEpic(epic);
        Integer epicId = taskManager.getEpics().getFirst().getId();
        SubTask newSubtask = new SubTask("name", "desc", epicId);
        taskManager.createSubtask(newSubtask);
        Integer subtaskId = taskManager.getSubtasks().getFirst().getId();
        request = HttpRequest.newBuilder(uri.resolve("./subtasks/" + subtaskId)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    public void nonExistingSubtaskGetByIdIs404() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder(uri.resolve("./subtasks/12312")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(404, response.statusCode());
    }

    @Test
    public void subTaskCreationIs201() throws IOException, InterruptedException {
        Assertions.assertEquals(0, taskManager.getSubtasks().size());
        request = HttpRequest.newBuilder(uri.resolve("./subtasks"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(subTask1Json, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals(1, taskManager.getSubtasks().size());

    }

    @Test
    public void subTaskUpdateIs201() throws IOException, InterruptedException {
        taskManager.createSubtask(subTask1);
        Assertions.assertEquals(1, taskManager.getSubtasks().size());
        SubTask subTask2 = taskManager.getSubtaskById(subTask1.getId());
        subTask2.setDescription("my new desc");
        String subTask2Json = gson.toJson(subTask2);
        request = HttpRequest.newBuilder(uri.resolve("./subtasks"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(subTask2Json, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals(1, taskManager.getSubtasks().size());

    }

    @Test
    public void subTaskOverlappingIs406() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder(uri.resolve("./subtasks"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(subTask1Json, StandardCharsets.UTF_8))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(406, response.statusCode());
    }

    @Test
    public void subTaskDeletionIs200() throws IOException, InterruptedException {
        taskManager.createEpic(epic);
        Epic epicFromMemory = taskManager.getEpics().getFirst();
        SubTask newSubtask = new SubTask("name", "desc", epicFromMemory.getId());
        taskManager.createSubtask(newSubtask);
        Assertions.assertEquals(1, taskManager.getSubtasks().size());

        Integer subtaskId = taskManager.getSubtasks().getFirst().getId();
        request = HttpRequest.newBuilder(uri.resolve("./subtasks/" + subtaskId))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(0, taskManager.getSubtasks().size());
    }
}
