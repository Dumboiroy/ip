package com.dumboiroy.seeyes;

import java.util.Scanner;

import com.dumboiroy.seeyes.command.Command;
import com.dumboiroy.seeyes.command.CommandResult;
import com.dumboiroy.seeyes.exception.InvalidCommandException;
import com.dumboiroy.seeyes.exception.SeeyesException;
import com.dumboiroy.seeyes.parser.Parser;
import com.dumboiroy.seeyes.storage.Storage;
import com.dumboiroy.seeyes.task.Task;
import com.dumboiroy.seeyes.task.TaskList;
import com.dumboiroy.seeyes.ui.Ui;

public class Seeyes {

    private TaskList taskList;
    private Storage storage;
    private Scanner scanner;
    private Ui ui;
    private Parser parser;

    // private enum Command {
    // LIST("list"), TODO("todo"), DEADLINE("deadline"), EVENT("event"),
    // MARK("mark"), UNMARK("unmark"), DELETE(
    // "delete"), SAVE("save"), LOAD("load"), HELP("/help"), BYE("bye");

    // private final String keyword;

    // Command(String keyword) {
    // this.keyword = keyword;
    // }

    // public static Command fromString(String commandString) throws
    // InvalidCommandException {
    // for (Command c : Command.values()) {
    // if (c.keyword.equalsIgnoreCase(commandString)) {
    // return c;
    // }
    // }
    // throw new InvalidCommandException(
    // "Sorry, I don't understand '" + commandString + "'. Type /help for a list of
    // commands.");
    // }
    // }

    public Seeyes(String filePath) {
        scanner = new Scanner(System.in);
        storage = new Storage(filePath);
        ui = Ui.getUi();

        try {
            taskList = storage.load();
        } catch (SeeyesException e) {
            ui.showError(e.getMessage());
            taskList = new TaskList();
        }
    }

    // public static void say(String str) {
    // System.out.println("Sy: " + str);
    // }

    // public static void print(String str) {
    // System.out.println(">> " + str);
    // }

    public void printListSize() {
        ui.say("Number of tasks in list: " + taskList.size());
    }

    public void printCommands() {
        ui.print("list: list all events", "todo [taskname]", "deadline [taskname] /by [duedate]",
                "event [taskname] /from [startdate] /to [enddate]", "mark [task number]: mark a task",
                "unmark [task number]: unmark a task", "delete [task number]: delete a task", "save: save list",
                "load: loads the list from existing save", "bye: closes the program");
    }

    public void printList() {
        CommandResult result;
        if (taskList.size() == 0) {
            result = new CommandResult("list is empty! add your first item with 'todo [item]'.");
            return;
        } else {
            result = new CommandResult("You have " + taskList.size() + " items in your list.", taskList.getTaskList());
        }
        ui.showResult(result);
    }

    public void addToList(Task task) {
        CommandResult result;
        if (taskList.addTask(task)) {
            result = new CommandResult("Added: " + task.toString());
        } else {
            result = new CommandResult("Failed to add: " + task.toString());
        }
        ui.showResult(result);
        printListSize();
    }

    // TODO: what does parseuserinput return?
    // what should executeCommand accept? type of command + arguments... maybe a
    // command class with fields?
    private CommandResult executeCommand(Command command) {
        // TODO: method to execure commands that accepts: Command command
        return command.execute();
    }

    public void run() {
        ui.showWelcomeMessage();

        String userInputString;
        while (true) {
            try {
                userInputString = ui.getNextUserCommand();
                CommandResult result = executeCommand(Parser.parseUserInput(userInputString).setData(taskList));
                ui.showResult(result);

                if (userInputString.equals("bye")) {
                    break;
                }
            } catch (InvalidCommandException e) {
                ui.showError(e.getMessage());
            }
            // try {
            // handleUserInput(userInputString);
            // } catch (InvalidCommandException e) {
            // ui.say(e.getMessage());
            // } catch (InvalidTaskNumberException e) {
            // ui.say(e.getMessage());
            // printList();
            // }

        }

        // Terminate
        scanner.close();
        exit();
    }

    private void exit() {
        ui.showFarewellMessage();
        System.exit(0);
    }

    public static void main(String[] args) {
        new Seeyes("./data/data.txt").run();
    }
}
