package seeyes.command;

import seeyes.exception.CommandFailedException;
import seeyes.exception.StorageException;

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
