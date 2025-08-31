package seeyes.command;

/**
 * Command to list all tasks.
 */
public class ListCommand extends Command {
    /**
     * Executes the list command.
     * 
     * @return the result of the command execution
     */
    @Override
    public CommandResult execute() {
        return new CommandResult("End of list.", taskList);
    }

}
