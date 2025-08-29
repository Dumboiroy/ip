package com.dumboiroy.seeyes.command;

import java.util.List;

import com.dumboiroy.seeyes.task.Task;
import com.dumboiroy.seeyes.task.TaskList;

public abstract class Command {
    protected TaskList taskList;
    protected List<? extends Task> resultTasks;

    public void setData(TaskList taskList, List<? extends Task> resultTasks) {
        this.taskList = taskList;
        this.resultTasks = resultTasks;
    }

    public abstract CommandResult execute();
}