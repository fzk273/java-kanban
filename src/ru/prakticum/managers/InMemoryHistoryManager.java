package ru.prakticum.managers;

import ru.prakticum.interfaces.HistoryManager;
import ru.prakticum.tasks.Task;

import java.util.*;


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
        if (node == null) return;
        if (node.prev == null) {
            head = node.next;
            if (head != null) {
                head.prev = null;
            }
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            tail = node.prev;
            if (tail != null) {
                tail.next = null;
            }
        } else {
            node.next.prev = node.prev;
        }
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        removeNode(history.get(task.getId()));
        Node taskNode = new Node(task);
        linkLast(taskNode);
        history.put(task.getId(), taskNode);
    }

    @Override
    public void remove(int id) {
        Node node = history.get(id);
        removeNode(node);
        history.remove(id);
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
}
