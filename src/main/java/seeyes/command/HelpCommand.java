package seeyes.command;

/**
 * Command to display help information.
 */
public class HelpCommand extends Command {
    /**
     * Executes the help command.
     *
     * @return the result of the command execution
     */
    @Override
    public CommandResult execute() {
        return new CommandResult("list: list all events" + "\n" + "todo [taskname]" + "\n"
                + "deadline [taskname] /by [duedate]" + "\n" + "event [taskname] /from [startdate] /to [enddate]" + "\n"
                + "mark [task number]: mark a task" + "\n" + "unmark [task number]: unmark a task" + "\n"
                + "delete [task number]: delete a task" + "\n" + "save: save list" + "\n"
                + "load: loads the list from existing save" + "\n" + "bye: closes the program");
    }
}
