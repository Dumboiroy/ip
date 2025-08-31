package seeyes.command;

/**
 * Command representing an invalid or unrecognized user input.
 * Returns an error message when executed.
 */
public class IncorrectCommand extends Command {
    private final String errorMessage;

    public IncorrectCommand(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public CommandResult execute() {
        return new CommandResult(errorMessage);
    }

}
