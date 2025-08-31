package seeyes.command;

import seeyes.exception.CommandFailedException;
import seeyes.exception.StorageException;

/**
 * Command to load tasks from storage.
 * Reads the task list from the configured storage location.
 */
public class LoadCommand extends Command {
    @Override
    public CommandResult execute() throws CommandFailedException {
        try {
            return new CommandResult("Load Success.", storage.load());
        } catch (StorageException e) {
            throw new CommandFailedException(e.getMessage());
        }
    }
}
