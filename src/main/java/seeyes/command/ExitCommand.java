package seeyes.command;

/**
 * Command to exit the application.
 */
public class ExitCommand extends Command {
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
