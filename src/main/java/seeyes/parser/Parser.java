package seeyes.parser;

import java.util.Arrays;

import seeyes.command.AddTaskCommand;
import seeyes.command.Command;
import seeyes.command.DeleteCommand;
import seeyes.command.ExitCommand;
import seeyes.command.FindCommand;
import seeyes.command.HelpCommand;
import seeyes.command.IncorrectCommand;
import seeyes.command.ListCommand;
import seeyes.command.LoadCommand;
import seeyes.command.MarkCommand;
import seeyes.command.SaveCommand;
import seeyes.command.UnmarkCommand;
import seeyes.exception.CommandFailedException;
import seeyes.exception.InvalidCommandException;
import seeyes.task.Task;
import seeyes.util.DateTimeUtils;

/**
 * Parses user input into commands.
 */
public class Parser {
    private enum CommandType {
        LIST("list"), FIND("find"), TODO("todo"), DEADLINE("deadline"), EVENT("event"), MARK("mark"), UNMARK("unmark"),
        DELETE("delete"), SAVE("save"), LOAD("load"), HELP("/help"), BYE("bye");

        private final String keyword;

        CommandType(String keyword) {
            this.keyword = keyword;
        }

        /**
         * Extracts a CommandType from a given command string.
         *
         * @param commandString
         *            the command string
         * @return the corresponding CommandType
         * @throws InvalidCommandException
         *             if the command is not recognized
         */
        public static CommandType fromString(String commandString) throws InvalidCommandException {
            for (CommandType c : CommandType.values()) {
                if (c.keyword.equalsIgnoreCase(commandString)) {
                    return c;
                }
            }
            throw new InvalidCommandException(
                    "Sorry, I don't understand '" + commandString + "'. Type /help for a list of commands.");
        }
    }

    /**
     * Parses user input string into a command object.
     *
     * @param userInputString
     *            the user input to parse
     * @return the parsed command
     * @throws InvalidCommandException
     *             if the input is invalid
     */
    public static Command parseUserInput(String userInputString) throws InvalidCommandException {
        // get command
        String[] split = userInputString.split(" ", 2);
        CommandType commandType = CommandType.fromString(split[0].trim());

        // depending on command, get args
        try {
            String[] params;
            switch (commandType) {
            case MARK:
                return new MarkCommand(parseTaskIndex(getArgs(split, split[0].trim() + " <task number>")));
            case UNMARK:
                return new UnmarkCommand(parseTaskIndex(getArgs(split, split[0].trim() + " <task number>")));
            case DELETE:
                return new DeleteCommand(parseTaskIndex(getArgs(split, split[0].trim() + " <task number>")));
            case TODO:
                params = parseTaskParams(commandType, getArgs(split, split[0].trim() + " <task name>"));
                return new AddTaskCommand(Task.of(params[0]));
            case DEADLINE:
                params = parseTaskParams(commandType, getArgs(split, split[0].trim() + " <task name>"));
                return new AddTaskCommand(Task.of(params[0], DateTimeUtils.parse(params[1])));
            case EVENT:
                params = parseTaskParams(commandType, getArgs(split, split[0].trim() + " <task name>"));
                return new AddTaskCommand(
                        Task.of(params[0], DateTimeUtils.parse(params[1]), DateTimeUtils.parse(params[2])));
            case SAVE:
                return new SaveCommand();
            case LOAD:
                return new LoadCommand();
            case HELP:
                return new HelpCommand();
            case LIST:
                return new ListCommand();
            case FIND:
                params = parseTaskParams(commandType, getArgs(split, split[0].trim() + " <task name>"));
                return new FindCommand(params[0]);
            case BYE:
                return new ExitCommand();
            default:
                return new IncorrectCommand("shouldn't reach here.");
            }
        } catch (CommandFailedException e) {
            throw new CommandFailedException(e.getMessage());
        }
    }

    /**
     * Extracts arguments from split command.
     *
     * @param split
     *            the split command array
     * @param usage
     *            the usage string for error messages
     * @return the arguments string
     * @throws InvalidCommandException
     *             if no arguments are provided
     */
    private static String getArgs(String[] split, String usage) throws InvalidCommandException {
        if (split.length < 2) {
            throw new InvalidCommandException("USAGE: " + usage);
        }
        return split[1].trim();
    }

    /**
     * Parses a task index from string.
     *
     * @param indexString
     *            the index string to parse
     * @return the zero-based index
     * @throws InvalidCommandException
     *             if the string is not a valid number
     */
    private static int parseTaskIndex(String indexString) throws InvalidCommandException {
        try {
            return Integer.parseInt(indexString) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("'" + indexString + "' is not a number. Please specify a task number.");
        }
    }

    /**
     * Parses parameters for task creation commands.
     *
     * @param taskType
     *            the type of task
     * @param paramString
     *            the parameter string to parse
     * @return array of parsed parameters
     * @throws InvalidCommandException
     *             if parameters are invalid
     */
    private static String[] parseTaskParams(CommandType taskType, String paramString) throws InvalidCommandException {
        String[] params;
        switch (taskType) {
        case TODO:
        case FIND:
            return new String[] { paramString };
        case DEADLINE:
            params = Arrays.stream(paramString.split("/by")).map(String::trim).toArray(String[]::new);
            if (params.length < 2) {
                throw new InvalidCommandException("please specify a due date with '/by <duedate>'");
            }
            return params;
        case EVENT:
            params = Arrays.stream(paramString.split("/from")).flatMap(x -> Arrays.stream(x.split("/to")))
                    .map(String::trim).toArray(String[]::new);
            if (params.length < 3) {
                throw new InvalidCommandException(
                        "please specify both a START and END date with '/from <start> /to <end>'");
            }
            return params;
        default:
            // shouldn't reach here
            throw new InvalidCommandException("Invalid Task Type");
        }
    }

}
