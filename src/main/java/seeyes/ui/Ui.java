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

public class Ui {

    private static final String DIVIDER = "============================================================";
    private static final String LS = System.lineSeparator();
    private static final String SAY_LINE_PREFIX = ">> ";
    private static final String PRINT_LINE_PREFIX = "## ";
    private static final String USER_LINE_PREFIX = "";
    private static final String FORMAT_INDEXED_LIST_ITEM = "\t%1$d. %2$s";
    private final Scanner in;
    private final PrintStream out;

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

    public void say(String... message) {
        for (String m : message) {
            out.println(SAY_LINE_PREFIX + m.replace("\n", LS + SAY_LINE_PREFIX));
        }
    }

    public void print(String... message) {
        for (String m : message) {
            out.println(SAY_LINE_PREFIX + m.replace("\n", LS + PRINT_LINE_PREFIX));
        }
    }

    public void showError(String errorMessage) {
        out.println(SAY_LINE_PREFIX + "ERROR: " + errorMessage);
    }

    public void showResult(CommandResult result) {
        final Optional<List<? extends Task>> resultTasks = result.getResultTasks();
        if (resultTasks.isPresent()) {
            showResultTasks(resultTasks.get());
        }
        say(result.message, DIVIDER);
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
