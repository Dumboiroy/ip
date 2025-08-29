package com.dumboiroy.seeyes.task;

public class TodoTask extends Task {
    protected TodoTask(boolean isDone, String name) {
        super(isDone, name);
    }

    @Override
    public String getSaveString() {
        return "TD|" + super.getSaveString();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
