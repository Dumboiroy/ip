package seeyes.command;

import seeyes.exception.InvalidCommandException;

/**
 * Command to mark a task as completed.
 * Marks the task at the specified index as done.
 */
public class MarkCommand extends Command {
    private int indexToMark;

    public MarkCommand(int indexToMark) {
        this.indexToMark = indexToMark;
    }

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
