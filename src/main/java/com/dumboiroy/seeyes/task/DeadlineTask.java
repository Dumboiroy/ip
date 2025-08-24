package com.dumboiroy.seeyes.task;

public class DeadlineTask extends Task {
    private String dateDue;

    protected DeadlineTask(String name, String dateDue) {
        super(name);
        this.dateDue = dateDue;
    }

    @Override
    public String getSaveString() {
        return "DL|" + super.getSaveString() + dateDue + "|";
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: [" + dateDue + "])";
    }
}
