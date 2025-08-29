package com.dumboiroy.seeyes.parser;

import com.dumboiroy.seeyes.command.Command;
import com.dumboiroy.seeyes.command.MarkCommand;
import com.dumboiroy.seeyes.exception.InvalidCommandException;

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
        switch (commandType) {
        case MARK:
            return new MarkCommand(parseTaskIndex(split, userInputString));
        case UNMARK:
            // TODO:
            return new UnmarkCommand(parseTaskIndex(split, userInputString));
        case DELETE:
            // TODO:
            return new DeleteCommand(parseTaskIndex(split, userInputString));
        case TODO:
            // TODO:
            break;
        case DEADLINE:
            // TODO:
            break;
        case EVENT:
            // TODO:
            break;
        case SAVE:
            // TODO:
            break;
        case LOAD:
            // TODO:
            break;
        case HELP:
            // TODO:
            break;
        case LIST:
            // TODO:
            break;
        case BYE:
            // TODO:
            break;
        }

        // return full command
        return null;
    }

    private static int parseTaskIndex(String[] split, String usage) throws InvalidCommandException {
        if (split.length < 2) {
            throw new InvalidCommandException("USAGE: " + usage);
        }
        try {
            return Integer.parseInt(split[1].trim()) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("'" + split[1] + "' is not a number. Please specify a task number.");
        }
    }

}
