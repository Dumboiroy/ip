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
 * Handles all user interface interactions for the Seeyes application.
 * Manages input from users and output display, including formatted messages,
 * task lists, and error messages.
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

    /**
     * Creates a UI instance with the specified input and output streams.
     *
     * @param in the input stream for reading user input
     * @param out the output stream for displaying messages
     */
    public Ui(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    public static Ui getUi() {
        return new Ui(System.in, System.out);
    }

    private boolean shouldIgnore(String rawInputLine) {
        if (isCommentLine(rawInputLine)) {
            out.print("\n\n" + rawInputLine + LS);
        }
        return rawInputLine.trim().isEmpty() || isCommentLine(rawInputLine);
    }

    private boolean isCommentLine(String rawInputLine) {
        return rawInputLine.trim().matches("#.*");
    }

    /**
     * Gets the next user command from input.
     * Prompts the user and reads their input while ignoring empty lines and comments.
     *
     * @return the user's command as a string
     * @throws NoMoreCommandsException if no more input is available
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
     * Displays messages with the standard "say" prefix.
     * Each message is prefixed with ">>" and newlines are properly formatted.
     *
     * @param message the messages to display
     */
    public void say(String... message) {
        for (String m : message) {
            out.println(SAY_LINE_PREFIX + m.replace("\n", LS + SAY_LINE_PREFIX));
        }
    }

    /**
     * Displays messages with the standard "print" prefix.
     * Similar to say() but with different formatting for specific use cases.
     *
     * @param message the messages to display
     */
    public void print(String... message) {
        for (String m : message) {
            out.println(SAY_LINE_PREFIX + m.replace("\n", LS + PRINT_LINE_PREFIX));
        }
    }

    /**
     * Displays error messages with proper formatting.
     *
     * @param errorMessage the error message to display
     */
    public void showError(String errorMessage) {
        out.println(SAY_LINE_PREFIX + "ERROR: " + errorMessage);
    }

    /**
     * Displays command results including any tasks and messages.
     *
     * @param result the command result to display
     */
    public void showResult(CommandResult result) {
        final Optional<List<? extends Task>> resultTasks = result.getResultTasks();
        final Optional<TaskList> taskList = result.getTaskList();
        if (resultTasks.isPresent()) {
            showResultTasks(resultTasks.get());
        }
        if (taskList.isPresent()) {
            showResultTasks(taskList.get().getTaskList());
        }
        say(result.getMessage(), DIVIDER);
    }

    private void showResultTasks(List<? extends Task> tasks) {
        showAsIndexedList(tasks.stream().map(task -> task.toString()).toList());
    }

    private void showAsIndexedList(List<String> list) {
        final StringBuilder formatted = new StringBuilder();
        int displayIndex = 1;
        for (String item : list) {
            formatted.append(getIndexedListItem(displayIndex, item)).append("\n");
            displayIndex++;
        }
        print(formatted.toString());
    }

    private static String getIndexedListItem(int visibleIndex, String listItem) {
        return String.format(FORMAT_INDEXED_LIST_ITEM, visibleIndex, listItem);
    }

    public void showWelcomeMessage() {
        say(DIVIDER, "Yo, I'm Seeyes!", "How can I help?", DIVIDER);
    }

    public void showFarewellMessage() {
        say("Program exited successfully.", DIVIDER);
    }
}
