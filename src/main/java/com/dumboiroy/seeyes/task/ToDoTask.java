package com.dumboiroy.seeyes.task;

public class ToDoTask extends Task {
    protected ToDoTask(boolean isDone, String name) {
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
