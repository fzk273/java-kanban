package ru.prakticum.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.prakticum.HttpTaskServer;
import ru.prakticum.adapters.DurationAdapter;
import ru.prakticum.adapters.LocalDateTimeAdapter;
import ru.prakticum.interfaces.TaskManager;
import ru.prakticum.tasks.Epic;
import ru.prakticum.tasks.SubTask;
import ru.prakticum.tasks.Task;
import ru.prakticum.utils.Managers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.LocalDateTime;

public class InitHandlers {

    protected Task task;
    protected String taskJson;
    protected SubTask subTask;
    protected String subTaskJson;
    protected Epic epic;
    protected String epicJson;
    protected static TaskManager taskManager = Managers.getDefault();
    protected static HttpTaskServer httpTaskServer;
    protected Gson gson;
    protected URI uri;
    protected HttpRequest request;
    protected HttpClient client;

    @BeforeAll
    public static void initServer() throws IOException {
        httpTaskServer = new HttpTaskServer(taskManager);
        httpTaskServer.start();

    }

    @BeforeEach
    void init() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter()).create();
        uri = URI.create("http://localhost:8080");
        client = HttpClient.newHttpClient();
        LocalDateTime nowDateTime = LocalDateTime.now();
        Duration oneHour = Duration.ofHours(1);
        epic = new Epic("epic", "desc");
        epic.setStartTime(nowDateTime);
        epic.setDuration(oneHour);
        epicJson = gson.toJson(epic);

        task = new Task("task", "desc");
        task.setStartTime(nowDateTime.plusHours(2));
        task.setDuration(oneHour);
        taskJson = gson.toJson(task);

        subTask = new SubTask("Subtask", "desc1", 0);
        subTask.setStartTime(nowDateTime.plusHours(4));
        subTask.setDuration(oneHour);
        subTaskJson = gson.toJson(subTask);
    }

    @AfterEach
    public void cleanup() {
        taskManager.deleteEpics();
        taskManager.deleteTasks();
        taskManager.deleteSubtasks();
    }

    @AfterAll
    public static void terminate() {
        httpTaskServer.stop();
    }
}
