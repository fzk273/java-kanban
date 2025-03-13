package ru.prakticum.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler {
    protected void sendText(HttpExchange h, String text) throws IOException {
        Gson jsonBuilder = new GsonBuilder().create();
        String jsonResponce = jsonBuilder.toJson(text);
        byte[] resp = jsonResponce.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendCreated(HttpExchange h, String text) throws IOException {
        Gson jsonBuilder = new GsonBuilder().create();
        String jsonResponce = jsonBuilder.toJson(text);
        byte[] resp = jsonResponce.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(201, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    public void sendNotFound(HttpExchange h, String text) throws IOException {
        Gson jsonBuilder = new GsonBuilder().create();
        String jsonResponce = jsonBuilder.toJson(text);
        byte[] resp = jsonResponce.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(400, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    public void sendHasInteractions(HttpExchange h, String text) throws IOException {
        Gson jsonBuilder = new GsonBuilder().create();
        String jsonResponce = jsonBuilder.toJson(text);
        byte[] resp = jsonResponce.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(406, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

}