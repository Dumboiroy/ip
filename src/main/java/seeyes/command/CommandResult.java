package seeyes.command;

import java.util.List;
import java.util.Optional;

import seeyes.task.Task;
import seeyes.task.TaskList;

public class CommandResult {
    public String message;
    private TaskList taskList;
    private List<? extends Task> resultTasks;

    public CommandResult(String message) {
        this.message = message;
    }

    public CommandResult(String message, TaskList taskList) {
        this.message = message;
        this.taskList = taskList;
    }

    public CommandResult(String message, List<? extends Task> resultTasks) {
        this.message = message;
        this.resultTasks = resultTasks;
    }

    public Optional<List<? extends Task>> getResultTasks() {
        return Optional.ofNullable(resultTasks);
    }

    public Optional<TaskList> getTaskList() {
        return Optional.ofNullable(taskList);
    }

}
