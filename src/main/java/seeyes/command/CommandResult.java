package seeyes.command;

import java.util.List;
import java.util.Optional;

import seeyes.task.Task;
import seeyes.task.TaskList;

/**
 * Represents the result of executing a command.
 */
public class CommandResult {
    /** The message to display to the user. */
    public String message;
    private TaskList taskList;
    private List<? extends Task> resultTasks;

    /**
     * Creates a command result with just a message.
     * 
     * @param message the message to display
     */
    public CommandResult(String message) {
        this.message = message;
    }

    /**
     * Creates a command result with a message and task list.
     * 
     * @param message the message to display
     * @param taskList the updated task list
     */
    public CommandResult(String message, TaskList taskList) {
        this.message = message;
        this.taskList = taskList;
    }

    /**
     * Creates a command result with a message and result tasks.
     * 
     * @param message the message to display
     * @param resultTasks the tasks to display
     */
    public CommandResult(String message, List<? extends Task> resultTasks) {
        this.message = message;
        this.resultTasks = resultTasks;
    }

    /**
     * Gets the result tasks if present.
     * 
     * @return optional containing result tasks
     */
    public Optional<List<? extends Task>> getResultTasks() {
        return Optional.ofNullable(resultTasks);
    }

    /**
     * Gets the task list if present.
     * 
     * @return optional containing task list
     */
    public Optional<TaskList> getTaskList() {
        return Optional.ofNullable(taskList);
    }

}
