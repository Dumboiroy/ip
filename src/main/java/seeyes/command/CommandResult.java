package seeyes.command;

import java.util.List;
import java.util.Optional;

import seeyes.task.Task;
import seeyes.task.TaskList;

/**
 * Represents the result of executing a command.
 * Contains the result message and optionally a task list or result tasks to display.
 */
public class CommandResult {
    private String message;
    private TaskList taskList;
    private List<? extends Task> resultTasks;

    public CommandResult(String message) {
        this.message = message;
    }

    /**
     * Creates a command result with a task list.
     *
     * @param message the result message
     * @param taskList the task list to include in the result
     */
    public CommandResult(String message, TaskList taskList) {
        this.message = message;
        this.taskList = taskList;
    }

    /**
     * Creates a command result with result tasks.
     *
     * @param message the result message
     * @param resultTasks the list of tasks to include in the result
     */
    public CommandResult(String message, List<? extends Task> resultTasks) {
        this.message = message;
        this.resultTasks = resultTasks;
    }

    public Optional<List<? extends Task>> getResultTasks() {
        return Optional.ofNullable(resultTasks);
    }

    public Optional<TaskList> getTaskList() {
        return Optional.ofNullable(taskList);
    }

    /**
     * Gets the result message.
     *
     * @return the result message
     */
    public String getMessage() {
        return message;
    }

}
