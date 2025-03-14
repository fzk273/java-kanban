package ru.prakticum.http;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PriorityHandlerTest extends InitHandlers {
    @BeforeAll
    public static void initServer() throws IOException {
        InitHandlers.initServer();
    }

    @BeforeEach
    public void init() {
        super.init();
        taskManager.createTask(task);
    }

    @AfterAll
    public static void terminate() {
        InitHandlers.terminate();
    }

    @Test
    public void priorityIs200() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder(uri.resolve("./prioritized")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }
}
