package seeyes.command;

import seeyes.exception.InvalidCommandException;

/**
 * Command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private int indexToMark;

    /**
     * Creates a delete command.
     * 
     * @param indexToMark the index of the task to delete
     */
    public DeleteCommand(int indexToMark) {
        this.indexToMark = indexToMark;
    }

    /**
     * Executes the delete command.
     * 
     * @return the result of the command execution
     */
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
