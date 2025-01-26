package ru.prakticum.managers;

import ru.prakticum.interfaces.HistoryManager;
import ru.prakticum.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryHistoryManager implements HistoryManager {
    private Map<Integer, Node> history;
    private Node head;
    private Node tail;


    public InMemoryHistoryManager() {
        this.history = new HashMap<>();
    }

    private class Node {
        Task task;
        Node prev;
        Node next;

        Node(Task task) {
            this.task = task;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "task=" + task.getId() +
                    ", prev=" + prev +
                    ", next=" + next +
                    '}';
        }
    }

    private void linkLast(Node node) {
        if (node == null) return;
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
    }

    private void removeNode(Node node) {
        if (node != null) return;

        if (node.next != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.prev != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }


    @Override
    public void add(Task task) {
        if (task == null) return;
        if (history.containsKey(task.getId())) {
            removeNode(history.get(task.getId()));
            history.remove(task.getId());
        }
        Node taskNode = new Node(task);
        linkLast(taskNode);
        history.put(task.getId(), taskNode);
    }

    @Override
    public void remove(int id) {
        if (history.containsKey(id)) {
            Node node = history.get(id);
            removeNode(node);
            history.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> historyList = new ArrayList<>();
        Node current = head;
        while (current != null) {
            historyList.add(current.task);
            current = current.next;
        }
        return historyList;
    }

    @Override
    public String toString() {
        return "InMemoryHistoryManager{" +
                "history=" + history +
                ", head=" + head +
                ", tail=" + tail +
                '}';
    }
}
