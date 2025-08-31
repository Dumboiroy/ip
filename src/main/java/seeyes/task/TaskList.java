package seeyes.task;

import java.util.ArrayList;
import java.util.List;

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
     * Gets the underlying list of tasks.
     *
     * @return the list of tasks
     */
    public ArrayList<Task> getTaskList() {
        return list;
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
     * Returns a list of tasks whose names contain the specified query string.
     *
     * @param queryString
     *            the string to search for in each task's name
     * @return a list of matching tasks
     */
    public List<Task> queryName(String queryString) {
        return list.stream().filter(task -> task.filterName(queryString)).toList();
    }

}
