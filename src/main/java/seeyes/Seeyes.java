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

    /**
     * Prints the current size of the task list.
     */
    public void printListSize() {
        ui.say("Number of tasks in list: " + taskList.size());
    }

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
            result = new CommandResult(
                    "list is empty! add your first item with 'todo [item]'.");
            return;
        } else {
            result = new CommandResult(
                    "You have " + taskList.size() + " items in your list.",
                    taskList.getTaskList());
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
     * Runs the main application loop.
     */
    public void run() {
        ui.showWelcomeMessage();

        while (true) {
            try {
                Command command = Parser.parseUserInput(ui.getNextUserCommand())
                        .setData(taskList, storage);
                CommandResult result = command.execute();
                if (result.getTaskList().isPresent()) {
                    taskList = result.getTaskList().get();
                }
                ui.showResult(result);

                // Solution below inspired by https://github.com/donkoo24/ip/blob/master/src/main/java/lux/Lux.java
                if (command.isExit()) {
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
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        return "Seeyes heard: " + input;
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
