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

public class Seeyes {
    private TaskList taskList;
    private Storage storage;
    private Scanner scanner;
    private Ui ui;

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

    public void printListSize() {
        ui.say("Number of tasks in list: " + taskList.size());
    }

    public void setTaskList(TaskList taskList) {
        this.taskList = taskList;
    }

    public void printList() {
        CommandResult result = taskList.createListCommandResult();
        ui.showResult(result);
    }

    public void addToList(Task task) {
        CommandResult result = taskList.addTaskWithResult(task);
        ui.showResult(result);
        printListSize();
    }

    private CommandResult executeCommand(Command command) {
        return command.execute();
    }

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
