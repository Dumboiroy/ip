package seeyes.task;

import java.util.ArrayList;

/**
 * Represents a list of tasks.
 */
public class TaskList {
    private ArrayList<Task> list;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.list = new ArrayList<>();
    }

    /**
     * Gets the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int size() {
        return list.size();
    }

    /**
     * Gets a task by its index.
     *
     * @param index
     *            the index of the task
     * @return the task at the specified index
     */
    public Task getTaskByIndex(int index) {
        return list.get(index);
    }

    /**
     * Removes a task by its index.
     *
     * @param index
     *            the index of the task to remove
     * @return the removed task
     */
    public Task removeTaskByIndex(int index) {
        return list.remove(index);
    }

    /**
     * Adds a task to the list.
     *
     * @param task
     *            the task to add
     * @return true if the task was added successfully
     */
    public boolean addTask(Task task) {
        return list.add(task);
    }

    /**
     * Gets the underlying list of tasks.
     *
     * @return the list of tasks
     */
    public ArrayList<Task> getTaskList() {
        return list;
    }
}
