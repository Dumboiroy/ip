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
 * Seeyes is a chatbot that helps users manage their tasks with support for
 * todo items, deadlines, and events.
 */
public class Seeyes {
    private TaskList taskList;
    private Storage storage;
    private Scanner scanner;
    private Ui ui;

    /**
     * Constructs a new Seeyes instance with the specified data file path.
     * Initializes the scanner, storage, and UI components, then attempts to load
     * existing tasks from the data file.
     *
     * @param filePath the path to the data file for storing tasks
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
     * Prints the current number of tasks in the task list.
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
     * Sets the task list for this Seeyes instance.
     *
     * @param taskList the task list to set
     */
    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Displays all tasks in the current task list.
     * Shows a message indicating the list is empty if there are no tasks.
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
     * Adds a task to the task list and displays the result.
     * Also prints the updated list size after adding the task.
     *
     * @param task the task to add to the list
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

    private CommandResult executeCommand(Command command) {
        return command.execute();
    }

    /**
     * Starts the main application loop.
     * Shows welcome message, processes user commands until "bye" is entered,
     * then performs cleanup and exits.
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

    private void exit() {
        ui.showFarewellMessage();
        System.exit(0);
    }

    public static void main(String[] args) {
        new Seeyes("./data/data.txt").run();
    }
}
