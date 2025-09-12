package seeyes.command;

/**
 * Command to list all tasks.
 */
public class ListDeadlinesCommand extends Command {
    /**
     * Executes the list command.
     *
     * @return the result of the command execution
     */
    @Override
    public CommandResult execute() {
        return new CommandResult("Here are your deadlines, sorted by date.",
                taskList.getSortedDeadlineTasks());
    }

}
