package seeyes.command;

public class ListCommand extends Command {
    @Override
    public CommandResult execute() {
        return new CommandResult("End of list.", taskList);
    }

}
