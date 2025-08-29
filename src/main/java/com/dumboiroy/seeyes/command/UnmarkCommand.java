package com.dumboiroy.seeyes.command;

import com.dumboiroy.seeyes.exception.InvalidCommandException;

public class UnmarkCommand extends Command {
    private int indexToMark;

    public UnmarkCommand(int indexToMark) {
        this.indexToMark = indexToMark;
    }

    @Override
    public CommandResult execute() throws InvalidCommandException {
        try {
            taskList.getTaskByIndex(indexToMark).markAsNotDone();
            return new CommandResult(
                    "Bummer. Alright, I've unchecked:\n" + taskList.getTaskByIndex(indexToMark)
                            + "\nKeep your head up!");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandException("Task number " + (indexToMark + 1)
                    + " does not exist. Check tasklist with 'list' to see what tasks you have.");
        }
    }
}
