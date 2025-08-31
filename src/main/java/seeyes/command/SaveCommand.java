package seeyes.command;

import seeyes.exception.CommandFailedException;
import seeyes.exception.StorageException;

/**
 * Command to save the current task list to storage.
 * Persists all tasks to the configured storage location.
 */
public class SaveCommand extends Command {
    @Override
    public CommandResult execute() throws CommandFailedException {
        try {
            return new CommandResult(storage.save(taskList));
        } catch (StorageException e) {
            throw new CommandFailedException(e.getMessage());
        }
    }
}
