package seeyes.command;

import seeyes.task.Task;

/**
 * Command to add a task to the task list.
 */
public class AddTaskCommand extends Command {
    private Task task;

    /**
     * Creates an add task command.
     *
     * @param task
     *            the task to add
     */
    public AddTaskCommand(Task task) {
        this.task = task;
    }

    /**
     * Executes the add task command.
     *
     * @return the result of the command execution
     */
    @Override
    public CommandResult execute() {
        if (taskList.addTask(task)) {
            return new CommandResult("Successfully added:\n" + task);
        } else {
            return new CommandResult("Unable to add task.");
        }
    }
}
