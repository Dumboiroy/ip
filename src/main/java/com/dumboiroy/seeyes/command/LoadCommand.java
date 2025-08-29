package com.dumboiroy.seeyes.command;

import com.dumboiroy.seeyes.exception.CommandFailedException;
import com.dumboiroy.seeyes.exception.StorageException;

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
