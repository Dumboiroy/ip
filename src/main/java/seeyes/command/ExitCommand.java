package seeyes.command;

/**
 * Command to exit the application.
 */
public class ExitCommand extends Command {
    @Override
    public boolean isExit() {
        return true;
    }

    /**
     * Executes the exit command.
     *
     * @return the result of the command execution
     */
    @Override
    public CommandResult execute() {
        return new CommandResult("See you around!");
    }
}
