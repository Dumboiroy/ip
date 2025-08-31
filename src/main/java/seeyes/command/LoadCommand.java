package seeyes.command;

import seeyes.exception.CommandFailedException;
import seeyes.exception.StorageException;

/**
 * Command to load tasks from storage.
 */
public class LoadCommand extends Command {
    /**
     * Executes the load command.
     * 
     * @return the result of the command execution
     * @throws CommandFailedException if loading fails
     */
    @Override
    public CommandResult execute() throws CommandFailedException {
        try {
            return new CommandResult("Load Success.", storage.load());
        } catch (StorageException e) {
            throw new CommandFailedException(e.getMessage());
        }
    }
}
