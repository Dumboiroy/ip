package seeyes.command;

import java.util.List;

import seeyes.storage.Storage;
import seeyes.task.Task;
import seeyes.task.TaskList;

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