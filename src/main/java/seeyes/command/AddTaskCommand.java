package seeyes.command;

import seeyes.task.Task;

public class AddTaskCommand extends Command {
    private Task task;

    public AddTaskCommand(Task task) {
        this.task = task;
    }

    @Override
    public CommandResult execute() {
        return taskList.addTaskWithResult(task);
    }
}
