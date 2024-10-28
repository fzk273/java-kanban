import Enums.Status;
import Enums.TaskTypes;
import java.util.ArrayList;
import java.util.HashMap;


public class TaskManager {
    public HashMap<Integer, Task> tasks;
    public HashMap<Integer, Task> epics;
    public HashMap<Integer, Task> subtasks;
    public static Integer counter = 0;

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();
    }

    public Task createATask(TaskTypes type, String name, String description) {
        counter++;
        switch (type.name()) {
            case "EPIC":
                Epic newEpic = new Epic(name, description, Status.NEW);
                epics.put(counter, newEpic);
                return newEpic;
            case "TASK":
                Task newTask = new Task(name, description, Status.NEW);
                tasks.put(counter, newTask);
                return newTask;
        }
        return null;
    }


    public Task createATask(Integer parentId, TaskTypes type, String name, String description) {
        counter++;
        switch (type.name()) {
            case "SUBTASK":
                Subtask newSubTask = new Subtask(parentId, name, description, Status.NEW);
                subtasks.put(counter, newSubTask);
                return newSubTask;
        }
        return null;
    }


    public ArrayList<HashMap<Integer, Task>> getAllTasks() {
        ArrayList<HashMap<Integer, Task>> allTasks = new ArrayList<>();
        allTasks.add(this.epics);
        allTasks.add(this.tasks);
        allTasks.add(this.subtasks);
        return allTasks;
    }

    public Task getTaskById(Integer id) {
        Task task = null;
        if (epics.get(id) != null) {
            task = epics.get(id);
        } else if (tasks.get(id) != null) {
            task = tasks.get(id);
        } else if (subtasks.get(id) != null) {
            task = subtasks.get(id);
        }
        return task;
    }

    public void deleteAllTasks() {
        epics.clear();
        tasks.clear();
        subtasks.clear();

    }

    public void deleteATasksById(Integer id) {
        if (epics.get(id) != null) {
            epics.remove(id);
        } else if (tasks.get(id) != null) {
            tasks.remove(id);
        } else if (subtasks.get(id) != null) {
            subtasks.remove(id);
        }
    }

    public void updateATask(Integer id, Task newTask) {
        if (epics.get(id) != null) {
            epics.put(id, newTask);
        } else if (tasks.get(id) != null) {
            tasks.put(id, newTask);
        } else if (subtasks.get(id) != null) {
            subtasks.put(id, newTask);
        }

    }


    public HashMap<Integer,Subtask> getAllSubtasksInEpicById(Integer id) {
        HashMap<Integer,Subtask> epicSubtasks = new HashMap<>();
        subtasks.forEach((key, value) -> {
            if (value.getParentId().equals(id)){
                epicSubtasks.put(key, (Subtask) value);
            }
        });
        return epicSubtasks;
    }

    @Override
    public String toString() {
        return "TaskManager{" +
                "task=" + tasks +
                ", epic=" + epics +
                ", subtask=" + subtasks +
                '}';
    }
}
