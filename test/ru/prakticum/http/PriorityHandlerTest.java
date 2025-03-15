package ru.prakticum.http;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PriorityHandlerTest extends InitHandlers {

    @BeforeEach
    public void init() {
        super.init();
        taskManager.createTask(task);
    }

    @Test
    public void priorityIs200() throws IOException, InterruptedException {
        request = HttpRequest.newBuilder(uri.resolve("./prioritized")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(200, response.statusCode());
    }
}
