package seeyes.task;

/**
 * Represents a simple todo task without any time constraints.
 * This is the basic task type that only has a description and completion status.
 */
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
