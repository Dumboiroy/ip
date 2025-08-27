package com.dumboiroy.seeyes;

import com.dumboiroy.seeyes.exception.InvalidCommandException;
import com.dumboiroy.seeyes.exception.InvalidTaskNumberException;
import com.dumboiroy.seeyes.storage.Storage;
import com.dumboiroy.seeyes.task.Task;
import com.dumboiroy.seeyes.util.DateTimeUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Seeyes {
    private static final String divider = "============================================================";
    public ArrayList<Task> taskList = new ArrayList<>();
    public Storage storage;
    private Scanner scanner;

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

    public static void printDivider() {
        System.out.println(divider);
    }

    public static void say(String str) {
        System.out.println("Sy: " + str);
    }

    public static void print(String str) {
        System.out.println(">> " + str);
    }

    public void printListSize() {
        print("Number of tasks in list: " + taskList.size());
    }

    public static void printCommands() {
        print("list: list all events");
        print("todo [taskname]");
        print("deadline [taskname] /by [duedate]");
        print("event [taskname] /from [startdate] /to [enddate]");
        print("mark [task number]: mark a task");
        print("unmark [task number]: unmark a task");
        print("delete [task number]: delete a task");
        print("save: save list");
        print("load: loads the list from existing save");
        print("bye: closes the program");

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
                    taskList.get(index).markAsDone();
                    say("Poggers. Let's check this off:\n" + taskList.get(index) + "\nKeep it up!");
                    break;
                case UNMARK:
                    taskList.get(index).markAsNotDone();
                    say("Shag. Ok, I've unmarked this task:\n " + taskList.get(index)
                            + "\nKeep your head up king.");
                    break;
                case DELETE:
                    Task toBeRemovedTask = taskList.get(index);
                    taskList.remove(index);
                    say("Ok bro let's get rid of it. REMOVED: " + toBeRemovedTask);
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
                    say("new deadline not added. needs at least a name and a due date.");
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
                    say("new event not added. specify a start and end date for this event.");
                    return;
                }
                String[] event_dates_arr = event_params[1].split("/to");
                if (event_dates_arr.length < 2) {
                    say("new event not added. specify a start and end date for this event.");
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
            say("unhandled command: " + command);
        }
    }

    public void printList() {
        if (taskList.size() == 0) {
            say("list is empty! add your first item with 'todo [item]'.");
            return;
        }
        say("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i) != null) {
                print((i + 1) + ". " + taskList.get(i));
            }
        }
    }

    public void addToList(Task task) {
        taskList.add(task);
        say("Added: " + task);
        printListSize();
    }

    public Seeyes(String filePath) {
        this.scanner = new Scanner(System.in);
        this.taskList = new ArrayList<>();
        this.storage = new Storage(filePath);
    }

    public void run() {
        printDivider();
        say("Yo, I'm Seeyes!");
        say("How can I help?");
        printDivider();
        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("bye")) {
                break;
            }
            try {
                handleUserInput(userInput);
            } catch (InvalidCommandException e) {
                say(e.getMessage());
            } catch (InvalidTaskNumberException e) {
                say(e.getMessage());
                printList();
            }
            printDivider();
        }
        say("See you around bro!");
        printDivider();

        // Terminate
        scanner.close();
    }

    public static void main(String[] args) {
        new Seeyes("./data/data.txt").run();

    }
}
