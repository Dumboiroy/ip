package com.dumboiroy.seeyes.command;

public class ExitCommand extends Command {
    @Override
    public CommandResult execute() {
        return new CommandResult("See you around!");
    }
}
