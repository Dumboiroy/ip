package seeyes.command;

import seeyes.exception.CommandFailedException;
import seeyes.exception.StorageException;

/**
 * Command to save tasks to storage.
 */
public class SaveCommand extends Command {
    /**
     * Executes the save command.
     * 
     * @return the result of the command execution
     * @throws CommandFailedException if saving fails
     */
    @Override
    public CommandResult execute() throws CommandFailedException {
        try {
            return new CommandResult(storage.save(taskList));
        } catch (StorageException e) {
            throw new CommandFailedException(e.getMessage());
        }
    }
}
