package com.dumboiroy.seeyes;

import com.dumboiroy.seeyes.exception.InvalidCommandException;
import com.dumboiroy.seeyes.exception.InvalidTaskNumberException;
import com.dumboiroy.seeyes.storage.StorageManager;
import com.dumboiroy.seeyes.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Seeyes {
    public static final String divider = "============================================================";
    public static ArrayList<Task> taskList = new ArrayList<>();
    public static StorageManager storage = new StorageManager("data.txt");

    private enum Command {
        LIST("list"), TODO("todo"), DEADLINE("deadline"), EVENT("event"), MARK("mark"), UNMARK("unmark"), DELETE(
                "delete"), SAVE("save"), HELP("/help"), BYE("bye");

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
        System.out.println("> " + str);
    }

    public static void printListSize() {
        say("Number of tasks in list: [" + taskList.size() + "]");
    }

    public static void printCommands() {
        System.out.println("list: list all events");
        System.out.println("todo [taskname]");
        System.out.println("deadline [taskname] /by [duedate]");
        System.out.println("event [taskname] /from [startdate] /to [enddate]");
        System.out.println("mark [task number]: mark a task");
        System.out.println("unmark [task number]: unmark a task");
        System.out.println("delete [task number]: delete a task");
        System.out.println("save: save list to disk");
        System.out.println("bye: closes the program");

    }

    public static void handleUserInput(String input) throws InvalidCommandException, InvalidTaskNumberException {
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
                throw new InvalidTaskNumberException("invalid task number: [" + (index + 1) + "]");
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
                String by = params[1].trim();
                addToList(Task.of(name, by));
                break;
            case EVENT:
                String[] extracted_name = paramsString.split("/from");
                if (extracted_name.length < 2) {
                    say("new event not added. specify a start and end date for this event.");
                    return;
                }
                String[] extracted_from = extracted_name[1].split("/to");
                if (extracted_from.length < 2) {
                    say("new event not added. specify a start and end date for this event.");
                    return;
                }
                addToList(Task.of(extracted_name[0].trim(), extracted_from[0].trim(),
                        extracted_from[1].trim()));
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
        case HELP:
            printCommands();
            break;
        case LIST:
            printList();
        case BYE:
            break;
        default:
            say("unhandled command: " + command);
        }
    }

    public static void printList() {
        if (taskList.size() == 0) {
            say("list is empty! add your first item with 'todo [item]'.");
        }
        say("Here are the tasks in your list:");
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i) != null) {
                say((i + 1) + ". " + taskList.get(i));
            }
        }
    }

    public static void addToList(Task task) {
        taskList.add(task);
        say("Added: " + task);
        printListSize();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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
}
