package com.dumboiroy.seeyes.command;

import com.dumboiroy.seeyes.task.Task;

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
