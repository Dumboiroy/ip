package seeyes;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import seeyes.command.Command;
import seeyes.command.CommandResult;
import seeyes.exception.InvalidCommandException;
import seeyes.exception.StorageException;
import seeyes.parser.Parser;
import seeyes.storage.Storage;
import seeyes.task.TaskList;
import seeyes.ui.Ui;

/**
 * Main class for the Seeyes task management application.
 */
public class Seeyes {
    private TaskList taskList;
    private Storage storage;
    private Ui ui;

    /**
     * Creates a new Seeyes application instance.
     *
     * @param filePath
     *            the path to the data file
     */
    public Seeyes(String filePath) {
        storage = new Storage(filePath, taskList);
        ui = Ui.getUi();

        // initial load
        try {
            setTaskList(storage.load());
        } catch (StorageException e) {
            ui.showError(e.getMessage());
            taskList = new TaskList();
        }
        assert taskList instanceof TaskList : "taskList should be an instance of taskList";
    }

    /**
     * Sets the task list.
     *
     * @param taskList
     *            the task list to set
     */
    public void setTaskList(TaskList taskList) {
        assert taskList instanceof TaskList : "taskList should be a TaskList instance.";
        this.taskList = taskList;
    }

    /**
     * Exits the application.
     */
    public void exit() {
        ui.showFarewellMessage();
        storage.save(taskList); // Autosave on exit
        waitThenExecute(1, () -> System.exit(0));
    }

    /**
     * Waits for the specified number of seconds, then executes the given action.
     *
     * @param seconds
     *            Number of seconds to wait
     * @param action
     *            The Runnable ()->() to execute after the delay
     */
    public void waitThenExecute(int seconds, Runnable action) {
        PauseTransition exitDelay = new PauseTransition(
                Duration.seconds(seconds));
        exitDelay.setOnFinished(e -> action.run());
        exitDelay.play();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parseUserInput(input).setData(taskList,
                    storage);
            CommandResult result = command.execute();

            // Update taskList if command modified it
            assert result instanceof CommandResult : "result should be a CommandResult";
            if (result.getTaskList().isPresent()) {
                taskList = result.getTaskList().get();
            }

            // Use UI formatting for the result
            return ui.formatResult(result);

        } catch (InvalidCommandException e) {
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            return "An unexpected error occurred: " + e.getMessage();
        }
    }

    /**
     * Gets the UI instance.
     *
     * @return the UI instance
     */
    public Ui getUi() {
        return ui;
    }
}
