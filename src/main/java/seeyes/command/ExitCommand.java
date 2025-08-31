package seeyes.command;

/**
 * Command to exit the Seeyes application.
 * Returns a farewell message when executed.
 */
public class ExitCommand extends Command {
    @Override
    public CommandResult execute() {
        return new CommandResult("See you around!");
    }
}
