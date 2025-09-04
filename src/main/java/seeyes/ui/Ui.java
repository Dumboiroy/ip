package seeyes.ui;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

import seeyes.command.CommandResult;
import seeyes.exception.NoMoreCommandsException;
import seeyes.task.Task;
import seeyes.task.TaskList;

/**
 * The {@code Ui} class handles user interaction for the Seeyes application. It manages input and output streams,
 * displays messages, errors, and results, and provides utility methods for formatting output.
 * <p>
 * Key features:
 * <ul>
 * <li>Prompts and reads user commands, ignoring empty or comment lines.</li>
 * <li>Displays messages, errors, and command results in a formatted manner.</li>
 * <li>Supports indexed list formatting for displaying tasks.</li>
 * <li>Handles welcome and farewell messages.</li>
 * </ul>
 * <p>
 * Usage example:
 *
 * <pre>
 * Ui ui = Ui.getUi();
 * String command = ui.getNextUserCommand();
 * ui.say("Hello!");
 * </pre>
 */
public class Ui {

    private static final String DIVIDER = "============================================================";
    private static final String LS = System.lineSeparator();
    private static final String SAY_LINE_PREFIX = ">> ";
    private static final String PRINT_LINE_PREFIX = "## ";
    private static final String USER_LINE_PREFIX = "";
    private static final String FORMAT_INDEXED_LIST_ITEM = "\t%1$d. %2$s";
    private final Scanner in;
    private final PrintStream out;

    private Ui(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    /**
     * Creates and returns a default Ui instance using standard input and output streams. This is a factory method that
     * provides a convenient way to create a Ui instance with System.in and System.out.
     *
     * @return a new Ui instance configured with standard input and output
     */
    public static Ui getUi() {
        return new Ui(System.in, System.out);
    }

    /**
     * Determines whether the given input line should be ignored during command processing. Lines are ignored if they
     * are empty, contain only whitespace, or are comment lines (starting with #). Comment lines are echoed to output
     * before being ignored.
     *
     * @param rawInputLine
     *            the raw input line to check
     * @return true if the line should be ignored, false otherwise
     */
    private boolean shouldIgnore(String rawInputLine) {
        if (isCommentLine(rawInputLine)) {
            out.print("\n\n" + rawInputLine + LS);
        }
        return rawInputLine.trim().isEmpty() || isCommentLine(rawInputLine);
    }

    /**
     * Checks if the given input line is a comment line. A comment line is defined as a line that starts with '#' (after
     * trimming whitespace).
     *
     * @param rawInputLine
     *            the input line to check
     * @return true if the line is a comment line, false otherwise
     */
    private boolean isCommentLine(String rawInputLine) {
        return rawInputLine.trim().matches("#.*");
    }

    /**
     * Reads and returns the next user command from the input stream. This method prompts the user for input and
     * continues reading until a non-empty, non-comment line is entered. Empty lines and comment lines (starting with #)
     * are ignored.
     *
     * @return the next valid user command as a string
     * @throws NoMoreCommandsException
     *             if there are no more commands to read (e.g., EOF reached)
     */
    public String getNextUserCommand() throws NoMoreCommandsException {
        try {
            print("Enter a command:" + USER_LINE_PREFIX);
            String rawInputLine = in.nextLine();
            while (shouldIgnore(rawInputLine)) {
                rawInputLine = in.nextLine();
            }
            return rawInputLine;
        } catch (NoSuchElementException e) {
            throw new NoMoreCommandsException("No more commands.");
        }
    }

    /**
     * Displays one or more messages to the output stream with the standard message prefix. Each message is prefixed
     * with the SAY_LINE_PREFIX (">> ") and newlines within messages are properly formatted with the same prefix.
     *
     * @param message
     *            one or more messages to display
     */
    public void say(String... message) {
        for (String m : message) {
            out.println(
                    SAY_LINE_PREFIX + m.replace("\n", LS + SAY_LINE_PREFIX));
        }
    }

    /**
     * Displays one or more messages to the output stream with the print prefix. Each message is prefixed with the
     * SAY_LINE_PREFIX initially, then PRINT_LINE_PREFIX for continuation lines. This method is typically used for
     * command prompts.
     *
     * @param message
     *            one or more messages to display
     */
    public void print(String... message) {
        for (String m : message) {
            out.println(
                    SAY_LINE_PREFIX + m.replace("\n", LS + PRINT_LINE_PREFIX));
        }
    }

    /**
     * Displays an error message to the output stream with an "ERROR: " prefix. The error message is formatted with the
     * standard message prefix.
     *
     * @param errorMessage
     *            the error message to display
     */
    public void showError(String errorMessage) {
        out.println(SAY_LINE_PREFIX + "ERROR: " + errorMessage);
    }

    /**
     * Displays the result of a command execution. This method handles both task lists and individual task results from
     * the CommandResult. It displays any tasks in an indexed format and shows the result message with a divider.
     *
     * @param result
     *            the CommandResult containing the execution result and any associated tasks
     */
    public void showResult(CommandResult result) {
        final Optional<List<? extends Task>> resultTasks = result
                .getResultTasks();
        final Optional<TaskList> taskList = result.getTaskList();
        if (resultTasks.isPresent()) {
            showResultTasks(resultTasks.get());
        }
        if (taskList.isPresent()) {
            showResultTasks(taskList.get().getTaskList());
        }
        say(result.getMessage(), DIVIDER);
    }

    /**
     * Displays a list of tasks in an indexed format. Each task is converted to its string representation and displayed
     * with a number.
     *
     * @param tasks
     *            the list of tasks to display
     */
    private void showResultTasks(List<? extends Task> tasks) {
        showAsIndexedList(tasks.stream().map(task -> task.toString()).toList());
    }

    /**
     * Displays a list of strings as an indexed list. Each item is prefixed with a sequential number starting from 1.
     *
     * @param list
     *            the list of strings to display as an indexed list
     */
    private void showAsIndexedList(List<String> list) {
        final StringBuilder formatted = new StringBuilder();
        int displayIndex = 1;
        for (String item : list) {
            formatted.append(getIndexedListItem(displayIndex, item))
                    .append("\n");
            displayIndex++;
        }
        print(formatted.toString());
    }

    /**
     * Formats a single item for display in an indexed list. The item is formatted with a tab, sequential number, and
     * the item content.
     *
     * @param visibleIndex
     *            the 1-based index number to display
     * @param listItem
     *            the content of the list item
     * @return a formatted string ready for display
     */
    private static String getIndexedListItem(int visibleIndex,
            String listItem) {
        return String.format(FORMAT_INDEXED_LIST_ITEM, visibleIndex, listItem);
    }

    /**
     * Displays the welcome message when the application starts. Shows the application name and a greeting to the user.
     */
    public void showWelcomeMessage() {
        say(DIVIDER, "Yo, I'm Seeyes!", "How can I help?", DIVIDER);
    }

    /**
     * Displays the farewell message when the application exits. Shows a confirmation that the program has exited
     * successfully.
     */
    public void showFarewellMessage() {
        say("Program exited successfully.", DIVIDER);
    }

    /**
     * Formats the result of a command execution as a string for GUI display. This method handles both task lists and
     * individual task results from the CommandResult.
     *
     * @param result
     *            the CommandResult containing the execution result and any associated tasks
     * @return formatted string ready for GUI display
     */
    public String formatResult(CommandResult result) {
        StringBuilder formattedResult = new StringBuilder();

        final Optional<List<? extends Task>> resultTasks = result
                .getResultTasks();
        final Optional<TaskList> taskList = result.getTaskList();

        if (resultTasks.isPresent()) {
            formattedResult.append(formatTaskList(resultTasks.get()))
                    .append("\n");
        }
        if (taskList.isPresent()) {
            formattedResult.append(formatTaskList(taskList.get().getTaskList()))
                    .append("\n");
        }

        formattedResult.append(result.getMessage());
        return formattedResult.toString();
    }

    /**
     * Formats a list of tasks as an indexed string for GUI display.
     *
     * @param tasks
     *            the list of tasks to format
     * @return formatted string with indexed tasks
     */
    public String formatTaskList(List<? extends Task> tasks) {
        if (tasks.isEmpty()) {
            return "No tasks to display.";
        }

        StringBuilder formatted = new StringBuilder();
        int displayIndex = 1;
        for (Task task : tasks) {
            formatted.append(
                    String.format("%d. %s", displayIndex, task.toString()));
            if (displayIndex < tasks.size()) {
                formatted.append("\n");
            }
            displayIndex++;
        }
        return formatted.toString();
    }

    /**
     * Returns the welcome message as a string for GUI display.
     *
     * @return welcome message string
     */
    public String getWelcomeMessage() {
        return "Yo, I'm Seeyes!\nHow can I help?";
    }

    /**
     * Returns the farewell message as a string for GUI display.
     *
     * @return farewell message string
     */
    public String getFarewellMessage() {
        return "Program exited successfully.";
    }
}
