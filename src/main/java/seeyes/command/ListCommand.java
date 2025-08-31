package seeyes.command;

public class ListCommand extends Command {
    @Override
    public CommandResult execute() {
        return taskList.createListCommandResult();
    }
}
