package com.dumboiroy.seeyes;

import java.time.LocalDateTime;
import java.util.Scanner;

import com.dumboiroy.seeyes.command.CommandResult;
import com.dumboiroy.seeyes.exception.InvalidCommandException;
import com.dumboiroy.seeyes.exception.InvalidTaskNumberException;
import com.dumboiroy.seeyes.exception.SeeyesException;
import com.dumboiroy.seeyes.storage.Storage;
import com.dumboiroy.seeyes.task.Task;
import com.dumboiroy.seeyes.task.TaskList;
import com.dumboiroy.seeyes.ui.Ui;
import com.dumboiroy.seeyes.util.DateTimeUtils;

public class Seeyes {

    private TaskList taskList;
    private Storage storage;
    private Scanner scanner;
    private Ui ui;

    private enum Command {
        LIST("list"), TODO("todo"), DEADLINE("deadline"), EVENT("event"), MARK("mark"), UNMARK("unmark"), DELETE(
                "delete"), SAVE("save"), LOAD("load"), HELP("/help"), BYE("bye");

        private final String keyword;

        Command(String keyword) {
            this.keyword = keyword;
        }

        public static Command fromString(String commandString) throws InvalidCommandException {
            for (Command c : Command.values()) {
                if (c.keyword.equalsIgnoreCase(commandString)) {
                    return c;
                }
            }
            throw new InvalidCommandException(
                    "Sorry, I don't understand '" + commandString + "'. Type /help for a list of commands.");
        }
    }

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

    public void handleUserInput(String input) throws InvalidCommandException, InvalidTaskNumberException {
        String[] split = input.split(" ", 2);
        Command command = Command.fromString(split[0].trim());
        if (split.length < 2) {
            switch (command) {
            case MARK:
            case UNMARK:
            case DELETE:
                throw new InvalidTaskNumberException("USAGE: mark/unmark/delete [task number]");
            case TODO:
            case DEADLINE:
            case EVENT:
                String errorMsg = switch (command) {
                case TODO -> "Please enter a name for your todo task. Usage: 'todo [name]'";
                case DEADLINE -> "Usage: 'deadline [name] /by [due date]'";
                case EVENT -> "Usage: 'event [name] /from [start date] /to [end date]'";
                default -> "";
                };
                throw new InvalidCommandException(errorMsg);
            default:
                break;
            }
        }
        switch (command) {
        case MARK:
        case UNMARK:
        case DELETE:
            try {
                Integer.parseInt(split[1]);
            } catch (NumberFormatException e) {
                String indexString = split[1];
                throw new InvalidTaskNumberException("'" + indexString + "' is not a number");
            }
            // mark, unmark or delete tasks
            String indexString = split[1];
            int index = Integer.parseInt(indexString) - 1;
            if (index >= 0 && index < taskList.size()) {
                switch (command) {
                case MARK:
                    taskList.getTaskByIndex(index).markAsDone();
                    ui.say("Poggers. Let's check this off:\n" + taskList.getTaskByIndex(index), "Keep it up!");
                    break;
                case UNMARK:
                    taskList.getTaskByIndex(index).markAsNotDone();
                    ui.say("Shag. Ok, I've unmarked this task:", taskList.getTaskByIndex(index).toString(),
                            "Keep your head up king.");
                    break;
                case DELETE:
                    Task toBeRemovedTask = taskList.getTaskByIndex(index);
                    taskList.removeTaskByIndex(index);
                    ui.say("Ok bro let's get rid of it. REMOVED: " + toBeRemovedTask);
                    printListSize();
                    break;
                default:
                    break;
                }
            } else {
                throw new InvalidTaskNumberException("invalid task number: " + (index + 1));
            }
            break;
        case TODO:
        case DEADLINE:
        case EVENT:
            // try {
            String paramsString = split[1].trim();
            // add task to list of tasks
            switch (command) {
            case TODO:
                addToList(Task.of(paramsString.trim()));
                break;
            case DEADLINE:
                String[] params = paramsString.split("/by");
                if (params.length < 2) {
                    ui.say("new deadline not added. needs at least a name and a due date.");
                    return;
                }
                String name = params[0].trim();
                String byString = params[1].trim();
                LocalDateTime by = DateTimeUtils.parse(byString);

                addToList(Task.of(name, by));
                break;
            case EVENT:
                String[] event_params = paramsString.split("/from");
                if (event_params.length < 2) {
                    ui.say("new event not added. specify a start and end date for this event.");
                    return;
                }
                String[] event_dates_arr = event_params[1].split("/to");
                if (event_dates_arr.length < 2) {
                    ui.say("new event not added. specify a start and end date for this event.");
                    return;
                }
                addToList(Task.of(event_params[0].trim(), DateTimeUtils.parse(event_dates_arr[0].trim()),
                        DateTimeUtils.parse(event_dates_arr[1].trim())));
                break;
            default:
                return;
            }
            ;
            // }
            break;
        case SAVE:
            storage.save(taskList);
            break;
        case LOAD:
            taskList = storage.load();
            break;
        case HELP:
            printCommands();
            break;
        case LIST:
            printList();
            break;
        case BYE:
            break;
        default:
            ui.say("unhandled command: " + command);
        }
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

    public void run() {
        ui.showWelcomeMessage();
        // ui up to here
        while (true) {
            String userInput = ui.getNextUserCommand();
            if (userInput.equals("bye")) {
                break;
            }
            try {
                handleUserInput(userInput);
            } catch (InvalidCommandException e) {
                ui.say(e.getMessage());
            } catch (InvalidTaskNumberException e) {
                ui.say(e.getMessage());
                printList();
            }

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
