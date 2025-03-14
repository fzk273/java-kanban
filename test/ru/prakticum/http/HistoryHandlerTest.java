package ru.prakticum.http;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HistoryHandlerTest extends InitHandlers {
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
    public void historyIs200() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder(uri.resolve("./history")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }
}
