package seeyes.command;

import seeyes.task.Task;

/**
 * Command to add a task to the task list.
 * Takes a task object and adds it to the current task list.
 */
public class AddTaskCommand extends Command {
    private Task task;

    public AddTaskCommand(Task task) {
        this.task = task;
    }

    @Override
    public CommandResult execute() {
        if (taskList.addTask(task)) {
            return new CommandResult("Successfully added:\n" + task);
        } else {
            return new CommandResult("Unable to add task.");
        }
    }
}
