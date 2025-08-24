package com.dumboiroy.seeyes.task;

public class ToDoTask extends Task {
    protected ToDoTask(String name) {
        super(name);
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
