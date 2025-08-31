package seeyes.task;

import java.util.ArrayList;

import seeyes.command.CommandResult;

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

    /**
     * Creates a CommandResult for listing all tasks, following the Information Expert principle.
     * TaskList knows best how to present itself.
     * 
     * @return CommandResult containing the list status and tasks
     */
    public CommandResult createListCommandResult() {
        if (size() == 0) {
            return new CommandResult("list is empty! add your first item with 'todo [item]'.");
        } else {
            return new CommandResult("You have " + size() + " items in your list.", this);
        }
    }

    /**
     * Adds a task and creates appropriate CommandResult, encapsulating the add operation.
     * 
     * @param task the task to add
     * @return CommandResult with success or failure message
     */
    public CommandResult addTaskWithResult(Task task) {
        if (addTask(task)) {
            return new CommandResult("Added: " + task.toString());
        } else {
            return new CommandResult("Failed to add: " + task.toString());
        }
    }
}
