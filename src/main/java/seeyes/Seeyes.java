package seeyes;

import java.util.Scanner;

import seeyes.command.Command;
import seeyes.command.CommandResult;
import seeyes.exception.InvalidCommandException;
import seeyes.exception.NoMoreCommandsException;
import seeyes.exception.StorageException;
import seeyes.parser.Parser;
import seeyes.storage.Storage;
import seeyes.task.Task;
import seeyes.task.TaskList;
import seeyes.ui.Ui;

/**
 * Main class for the Seeyes task management application.
 */
public class Seeyes {
    private TaskList taskList;
    private Storage storage;
    private Scanner scanner;
    private Ui ui;

    /**
     * Creates a new Seeyes application instance.
     *
     * @param filePath
     *            the path to the data file
     */
    public Seeyes(String filePath) {
        scanner = new Scanner(System.in);
        storage = new Storage(filePath, taskList);
        ui = Ui.getUi();

        // initial load
        try {
            setTaskList(storage.load());
        } catch (StorageException e) {
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

    /**
     * Prints the current size of the task list.
     */
    public void printListSize() {
        ui.say("Number of tasks in list: " + taskList.size());
    }

    // public void printCommands() {
    // ui.print("list: list all events", "todo [taskname]", "deadline [taskname]
    // /by
    // [duedate]",
    // "event [taskname] /from [startdate] /to [enddate]", "mark [task number]:
    // mark
    // a task",
    // "unmark [task number]: unmark a task", "delete [task number]: delete a
    // task",
    // "save: save list",
    // "load: loads the list from existing save", "bye: closes the program");
    // }

    /**
     * Sets the task list.
     *
     * @param taskList
     *            the task list to set
     */
    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Prints the current task list.
     */
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

    /**
     * Adds a task to the list and displays the result.
     *
     * @param task
     *            the task to add
     */
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

    /**
     * Executes a command and returns the result.
     *
     * @param command
     *            the command to execute
     * @return the result of the command execution
     */
    private CommandResult executeCommand(Command command) {
        return command.execute();
    }

    /**
     * Runs the main application loop.
     */
    public void run() {
        ui.showWelcomeMessage();

        String userInputString;
        while (true) {
            try {
                userInputString = ui.getNextUserCommand();
                CommandResult result = executeCommand(
                        Parser.parseUserInput(userInputString).setData(taskList, storage));
                if (result.getTaskList().isPresent()) {
                    taskList = result.getTaskList().get();
                }
                ui.showResult(result);

                if (userInputString.equals("bye")) {
                    break;
                }
            } catch (InvalidCommandException e) {
                ui.showError(e.getMessage());
            } catch (NoMoreCommandsException e) {
                ui.showError(e.getMessage());
                break;
            }
        }

        // Terminate
        scanner.close();
        exit();
    }

    /**
     * Exits the application.
     */
    private void exit() {
        ui.showFarewellMessage();
        System.exit(0);
    }

    /**
     * Main method to start the application.
     *
     * @param args
     *            command line arguments
     */
    public static void main(String[] args) {
        new Seeyes("./data/data.txt").run();
    }
}
