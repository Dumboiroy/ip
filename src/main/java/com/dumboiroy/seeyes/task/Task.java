package com.dumboiroy.seeyes.task;

public abstract class Task {
    private String name;
    private boolean isDone;

    protected Task(String name) {
        this.name = name;
        isDone = false;
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + name;
    }

    public static Task of(String name) {
        return new ToDoTask(name);
    }

    public static Task of(String name, String dateDue) {
        return new DeadlineTask(name, dateDue);
    }

    public static Task of(String name, String start, String end) {
        return new EventTask(name, start, end);
    }
}
