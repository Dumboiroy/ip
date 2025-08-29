package seeyes.command;

import seeyes.exception.InvalidCommandException;

public class DeleteCommand extends Command {
    private int indexToMark;

    public DeleteCommand(int indexToMark) {
        this.indexToMark = indexToMark;
    }

    @Override
    public CommandResult execute() {
        try {
            return new CommandResult("Okay, I've gotten rid of: " + taskList.removeTaskByIndex(indexToMark));
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandException("Task number " + (indexToMark + 1)
                    + " does not exist. Check tasklist with 'list' to see what tasks you have.");
        }
    }
}
