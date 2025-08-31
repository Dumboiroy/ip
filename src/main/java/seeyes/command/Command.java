package seeyes.command;

import java.util.List;

import seeyes.storage.Storage;
import seeyes.task.Task;
import seeyes.task.TaskList;

/**
 * Abstract base class for all commands in the Seeyes application.
 */
public abstract class Command {
    protected TaskList taskList;
    protected Storage storage;
    protected List<? extends Task> resultTasks;

    /**
     * Sets the task list and storage for this command.
     *
     * @param taskList
     *            the task list to operate on
     * @param storage
     *            the storage instance for persistence
     * @return this command instance for method chaining
     */
    public Command setData(TaskList taskList, Storage storage) {
        this.taskList = taskList;
        this.storage = storage;
        return this;
    }

    /**
     * Executes the command and returns the result.
     *
     * @return the result of executing the command
     */
    public abstract CommandResult execute();
}
