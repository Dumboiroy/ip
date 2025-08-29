package com.dumboiroy.seeyes.command;

import java.util.List;
import java.util.Optional;

import com.dumboiroy.seeyes.task.Task;

public class CommandResult {
    public String message;
    private List<? extends Task> resultTasks;

    public CommandResult(String message) {
        this.message = message;
        resultTasks = null;
    }

    public CommandResult(String message, List<? extends Task> resultTasks) {
        this.message = message;
        this.resultTasks = resultTasks;
    }

    public Optional<List<? extends Task>> getResultTasks() {
        return Optional.ofNullable(resultTasks);
    }
}
