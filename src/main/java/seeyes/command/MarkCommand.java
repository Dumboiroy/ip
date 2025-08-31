package seeyes.command;

import seeyes.exception.InvalidCommandException;

/**
 * Command to mark a task as done.
 */
public class MarkCommand extends Command {
    private int indexToMark;

    /**
     * Creates a mark command.
     *
     * @param indexToMark
     *            the index of the task to mark
     */
    public MarkCommand(int indexToMark) {
        this.indexToMark = indexToMark;
    }

    /**
     * Executes the mark command.
     *
     * @return the result of the command execution
     */
    @Override
    public CommandResult execute() {
        try {
            taskList.getTaskByIndex(indexToMark).markAsDone();
            return new CommandResult(
                    "Nice. Let's check this off:\n" + taskList.getTaskByIndex(indexToMark) + "\nKeep it up!");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommandException("Task number " + (indexToMark + 1)
                    + " does not exist. Check tasklist with 'list' to see what tasks you have.");
        }
    }
}
