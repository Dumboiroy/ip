package com.dumboiroy.ui;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.dumboiroy.seeyes.task.Task;

public class Ui {

    private static final String DIVIDER = "============================================================";
    private static final String LS = System.lineSeparator();
    private static final String LINE_PREFIX = ">> ";
    private final Scanner in;
    private final PrintStream out;

    public Ui(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    public static Ui newUi() {
        return new Ui(System.in, System.out);
    }

    private boolean shouldIgnore(String rawInputLine) {
        return rawInputLine.trim().isEmpty() || isCommentLine(rawInputLine);
    }

    private boolean isCommentLine(String rawInputLine) {
        return rawInputLine.trim().matches("#.*");
    }

    public String getNextUserCommand() {
        out.print(LINE_PREFIX + "Enter a command:\n");
        String rawInputLine = in.nextLine();
        while (shouldIgnore(rawInputLine)) {
            rawInputLine = in.nextLine();
        }
        return rawInputLine;
    }

    public void show(String... message) {
        for (String m : message) {
            out.println(LINE_PREFIX + m.replace("\n", LS + LINE_PREFIX));
        }
    }

    public void showError(String errorMessage) {
        out.println(LINE_PREFIX + "ERROR: " + errorMessage);
    }

    // public void showResult(CommandResult result) {
    // final Optional<List<? extends Task>> resultTasks = result.getResultTasks();
    // if (resultTasks.isPresent()) {
    // showResultTasks(resultTasks.get());
    // }
    // show(result.message, DIVIDER);
    // }

    // private void showResultTasks(List<? extends Task> resultTasks) {
    // final List<String> formattedTasks = new ArrayList<>();
    // for (Task task : resultTasks) {
    // formattedTasks.add(task.toString());
    // }
    // showAsIndexedList(formattedTasks);
    // }

    // private void showAsIndexedList(List<String> list) {
    // showToUser(getIndexedListView(list));
    // }

    // private static String getIndexedListView(List<String> list) {
    // final StringBuilder formatted = new StringBuilder();

    // }

    public void showWelcomeMessage() {
        show(
                DIVIDER,
                "Yo, I'm Seeyes!",
                "How can I help?",
                DIVIDER);
    }

    public void showFarewellMessage() {
        show(
                "See you around bro!",
                DIVIDER);
    }

    public static void main(String[] args) {
        Ui ui = Ui.newUi();
        ui.show("Hello", "Message 1", "bye");
    }
}
