package seeyes.task;

import java.util.ArrayList;

/**
 * Manages a list of tasks for the Seeyes application.
 * Provides methods to add, remove, and access tasks in the list.
 */
public class TaskList {
    private ArrayList<Task> list;

    public TaskList() {
        this.list = new ArrayList<>();
    }

    public int size() {
        return list.size();
    }

    public Task getTaskByIndex(int index) {
        return list.get(index);
    }

    public Task removeTaskByIndex(int index) {
        return list.remove(index);
    }

    public boolean addTask(Task task) {
        return list.add(task);
    }

    public ArrayList<Task> getTaskList() {
        return list;
    }
}
