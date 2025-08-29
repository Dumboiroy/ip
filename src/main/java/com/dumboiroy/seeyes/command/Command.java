package com.dumboiroy.seeyes.command;

import java.util.List;

import com.dumboiroy.seeyes.storage.Storage;
import com.dumboiroy.seeyes.task.Task;
import com.dumboiroy.seeyes.task.TaskList;

public abstract class Command {
    protected TaskList taskList;
    protected Storage storage;
    protected List<? extends Task> resultTasks;

    public Command setData(TaskList taskList, Storage storage) {
        this.taskList = taskList;
        this.storage = storage;
        return this;
    }

    public abstract CommandResult execute();
}