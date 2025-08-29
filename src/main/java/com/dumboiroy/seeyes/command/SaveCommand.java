package com.dumboiroy.seeyes.command;

public class SaveCommand extends Command {
    @Override
    public CommandResult execute() {
        return new CommandResult(storage.save(taskList));
    }
}
