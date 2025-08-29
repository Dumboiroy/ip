package com.dumboiroy.seeyes.parser;

import java.util.Arrays;

import com.dumboiroy.seeyes.command.AddTaskCommand;
import com.dumboiroy.seeyes.command.Command;
import com.dumboiroy.seeyes.command.DeleteCommand;
import com.dumboiroy.seeyes.command.IncorrectCommand;
import com.dumboiroy.seeyes.command.LoadCommand;
import com.dumboiroy.seeyes.command.MarkCommand;
import com.dumboiroy.seeyes.command.SaveCommand;
import com.dumboiroy.seeyes.command.UnmarkCommand;
import com.dumboiroy.seeyes.exception.CommandFailedException;
import com.dumboiroy.seeyes.exception.InvalidCommandException;
import com.dumboiroy.seeyes.task.Task;
import com.dumboiroy.seeyes.util.DateTimeUtils;

public class Parser {
    private enum CommandType {
        LIST("list"), TODO("todo"), DEADLINE("deadline"), EVENT("event"), MARK("mark"), UNMARK("unmark"), DELETE(
                "delete"), SAVE("save"), LOAD("load"), HELP("/help"), BYE("bye");

        private final String keyword;

        CommandType(String keyword) {
            this.keyword = keyword;
        }

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
                return new AddTaskCommand(Task.of(
                        params[0],
                        DateTimeUtils.parse(params[1])));
            case EVENT:
                params = parseTaskParams(commandType, getArgs(split, split[0].trim() + " <task name>"));
                return new AddTaskCommand(
                        Task.of(
                                params[0],
                                DateTimeUtils.parse(params[1]),
                                DateTimeUtils.parse(params[2])));
            case SAVE:
                return new SaveCommand();
            case LOAD:
                return new LoadCommand();
            case HELP:
                throw new InvalidCommandException("unimplemented help ");
            case LIST:
                throw new InvalidCommandException("unimplemented list");
            case BYE:
                throw new InvalidCommandException("unimplemented bye");
            default:
                return new IncorrectCommand("shouldn't be here.");
            }
        } catch (CommandFailedException e) {
            throw new CommandFailedException(e.getMessage());
        }
    }

    private static String getArgs(String[] split, String usage) throws InvalidCommandException {
        if (split.length < 2) {
            throw new InvalidCommandException("USAGE: " + usage);
        }
        return split[1].trim();
    }

    private static int parseTaskIndex(String indexString) throws InvalidCommandException {
        try {
            return Integer.parseInt(indexString) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("'" + indexString + "' is not a number. Please specify a task number.");
        }
    }

    private static String[] parseTaskParams(CommandType taskType, String paramString) throws InvalidCommandException {
        String[] params;
        switch (taskType) {
        case TODO:
            return new String[] { paramString };
        case DEADLINE:
            params = Arrays.stream(paramString.split("/by"))
                    .map(String::trim)
                    .toArray(String[]::new);
            if (params.length < 2) {
                throw new InvalidCommandException("please specify a due date with '/by <duedate>'");
            }
            return params;
        case EVENT:
            params = Arrays.stream(paramString.split("/from"))
                    .flatMap(x -> Arrays.stream(x.split("/to")))
                    .map(String::trim)
                    .toArray(String[]::new);
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
