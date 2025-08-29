package seeyes.command;

import seeyes.exception.CommandFailedException;
import seeyes.exception.StorageException;

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
