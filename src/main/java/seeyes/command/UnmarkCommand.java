package seeyes.command;

import seeyes.exception.InvalidCommandException;

/**
 * Command to unmark a task as not done.
 */
public class UnmarkCommand extends Command {
    private int indexToMark;

    /**
     * Creates an unmark command.
     * 
     * @param indexToMark the index of the task to unmark
     */
    public UnmarkCommand(int indexToMark) {
        this.indexToMark = indexToMark;
    }

    /**
     * Executes the unmark command.
     * 
     * @return the result of the command execution
     * @throws InvalidCommandException if the task index is invalid
     */
    @Override
    public CommandResult execute() throws InvalidCommandException {
        try {
            taskList.getTaskByIndex(indexToMark).markAsNotDone();
            return new CommandResult("Bummer. Alright, I've unchecked:\n" + taskList.getTaskByIndex(indexToMark)
                    + "\nKeep your head up!");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandException("Task number " + (indexToMark + 1)
                    + " does not exist. Check tasklist with 'list' to see what tasks you have.");
        }
    }
}
