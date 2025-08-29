package seeyes.command;

public class IncorrectCommand extends Command {
    private final String errorMessage;

    public IncorrectCommand(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public CommandResult execute() {
        return new CommandResult(errorMessage);
    }

}
