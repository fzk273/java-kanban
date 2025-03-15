package ru.prakticum.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.prakticum.adapters.DurationAdapter;
import ru.prakticum.adapters.LocalDateTimeAdapter;
import ru.prakticum.interfaces.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

public class BaseHttpHandler implements HttpHandler {
    protected Gson gson;
    protected TaskManager taskManager;

    public BaseHttpHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
        this.gson = new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter()).create();
    }

    protected void sendText(HttpExchange h, String text, Integer responseCode) throws IOException {
        Gson jsonBuilder = new GsonBuilder().create();
        String jsonResponce = jsonBuilder.toJson(text);
        byte[] resp = jsonResponce.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(responseCode, resp.length);
        //TODO если в случае ошибки отправлять без боди (как в комменте на пр) тогда запрос не возвращается корректно
        h.getResponseBody().write(resp);
        h.close();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
    }
}