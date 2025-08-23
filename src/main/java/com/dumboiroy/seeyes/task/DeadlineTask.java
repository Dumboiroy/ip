package com.dumboiroy.seeyes.task;

public class DeadlineTask extends Task {
    private String dateDue;

    public DeadlineTask(String name, String dateDue) {
        super(name);
        this.dateDue = dateDue;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: [" + dateDue + "])";
    }
}
