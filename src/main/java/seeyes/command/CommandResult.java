package seeyes.command;

import java.util.List;
import java.util.Optional;

import seeyes.task.Task;

public class CommandResult {
    public String message;
    private List<? extends Task> resultTasks;

    public CommandResult(String message) {
        this.message = message;
    }

    public CommandResult(String message, List<? extends Task> resultTasks) {
        this.message = message;
        this.resultTasks = resultTasks;
    }

    public Optional<List<? extends Task>> getResultTasks() {
        return Optional.ofNullable(resultTasks);
    }

}
