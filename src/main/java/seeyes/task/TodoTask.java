package seeyes.task;

/**
 * Represents a todo task.
 */
public class TodoTask extends Task {
    /**
     * Creates a todo task.
     * 
     * @param isDone whether the task is done
     * @param name the name of the task
     */
    protected TodoTask(boolean isDone, String name) {
        super(isDone, name);
    }

    /**
     * Gets the string representation for saving to file.
     * 
     * @return the save string
     */
    @Override
    public String getSaveString() {
        return "TD|" + super.getSaveString();
    }

    /**
     * Gets the string representation of the todo task.
     * 
     * @return the string representation
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
