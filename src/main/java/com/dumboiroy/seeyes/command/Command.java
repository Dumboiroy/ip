package com.dumboiroy.seeyes.command;

import java.util.List;

import com.dumboiroy.seeyes.task.Task;
import com.dumboiroy.seeyes.task.TaskList;

public abstract class Command {
    protected TaskList taskList;
    protected List<? extends Task> resultTasks;

    public Command setData(TaskList taskList) {
        this.taskList = taskList;
        return this;
    }

    public abstract CommandResult execute();
}