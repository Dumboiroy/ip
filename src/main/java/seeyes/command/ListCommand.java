package seeyes.command;

/**
 * Command to list all tasks in the task list.
 * Returns a result containing all current tasks.
 */
public class ListCommand extends Command {
    @Override
    public CommandResult execute() {
        return new CommandResult("End of list.", taskList);
    }

}
