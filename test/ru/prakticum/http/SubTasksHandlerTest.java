package ru.prakticum.http;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

public class SubTasksHandlerTest extends InitHandlers {
    private SubTask subtaskFromInit;
    private String subtaskFromInitJson;

    @BeforeEach
    public void init() {
        super.init();
        taskManager.createEpic(epic);
        Integer epicId = taskManager.getEpics().getFirst().getId();
        subtaskFromInit = new SubTask("subtask1", "subdesc1", epicId);
        subtaskFromInit.setDuration(Duration.ofHours(5));
        subtaskFromInit.setStartTime(LocalDateTime.now().plusHours(2));
        subtaskFromInitJson = gson.toJson(subtaskFromInit);
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
        JsonElement jsonElement = JsonParser.parseString(JsonParser.parseString(response.body()).getAsString());
        SubTask subtaskFromResponse = gson.fromJson(jsonElement, SubTask.class);
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(newSubtask.getDescription(), subtaskFromResponse.getDescription());

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
                .POST(HttpRequest.BodyPublishers.ofString(subtaskFromInitJson, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonElement jsonElement = JsonParser.parseString(JsonParser.parseString(response.body()).getAsString());
        SubTask subtaskFromResponse = gson.fromJson(jsonElement, SubTask.class);
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals(1, taskManager.getSubtasks().size());
        Assertions.assertEquals(subtaskFromInit.getDescription(), subtaskFromResponse.getDescription());

    }

    @Test
    public void subTaskUpdateIs201() throws IOException, InterruptedException {
        taskManager.createSubtask(subtaskFromInit);
        Assertions.assertEquals(1, taskManager.getSubtasks().size());
        SubTask newSubtask = taskManager.getSubtaskById(subtaskFromInit.getId());
        newSubtask.setDescription("my new desc");
        String newSubtaskJson = gson.toJson(newSubtask);
        request = HttpRequest.newBuilder(uri.resolve("./subtasks"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(newSubtaskJson, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonElement jsonElement = JsonParser.parseString(JsonParser.parseString(response.body()).getAsString());
        SubTask subtaskFromResponse = gson.fromJson(jsonElement, SubTask.class);
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals(1, taskManager.getSubtasks().size());
        Assertions.assertEquals(newSubtask.getDescription(), subtaskFromResponse.getDescription());

    }

    @Test
    public void subTaskOverlappingIs406() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder(uri.resolve("./subtasks"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(subtaskFromInitJson, StandardCharsets.UTF_8))
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
