package com.dumboiroy.seeyes.task;

public class DeadlineTask extends Task {
    private String dateDue;

    protected DeadlineTask(boolean isDone, String name, String dateDue) {
        super(isDone, name);
        this.dateDue = dateDue;
    }

    @Override
    public String getSaveString() {
        return "DL|" + super.getSaveString() + dateDue + "|";
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dateDue + ")";
    }
}
