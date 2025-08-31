package seeyes.command;

/**
 * Command representing an incorrect or invalid command.
 */
public class IncorrectCommand extends Command {
    private final String errorMessage;

    /**
     * Creates an incorrect command with an error message.
     *
     * @param errorMessage
     *            the error message to display
     */
    public IncorrectCommand(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Executes the incorrect command.
     *
     * @return the result of the command execution
     */
    @Override
    public CommandResult execute() {
        return new CommandResult(errorMessage);
    }

}
