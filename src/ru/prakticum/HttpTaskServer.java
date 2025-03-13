package ru.prakticum;

import com.sun.net.httpserver.HttpServer;
import ru.prakticum.handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private final Integer portNumber = 8080;

    public void main() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(portNumber), 0);
        httpServer.createContext("/tasks", new TasksHandler());
        httpServer.createContext("/subtasks", new SubtasksHandler());
        httpServer.createContext("/epics", new EpicsHandler());
        httpServer.createContext("/history", new HistoryHandler());
        httpServer.createContext("/prioritized", new PriorityHandler());
        httpServer.start();
    }
}
