package com.dumboiroy.seeyes.command;

public class ListCommand extends Command {
    @Override
    public CommandResult execute() {
        return new CommandResult("Here's your list of tasks:", taskList);
    }

}
