package seeyes.task;

import java.time.LocalDateTime;

import seeyes.util.DateTimeUtils;

/**
 * Represents a task with a deadline.
 */
public class DeadlineTask extends Task {
    private LocalDateTime dateDue;

    /**
     * Creates a deadline task.
     *
     * @param isDone
     *            whether the task is done
     * @param name
     *            the name of the task
     * @param dateDue
     *            the due date
     */
    protected DeadlineTask(boolean isDone, String name, LocalDateTime dateDue) {
        super(isDone, name);
        this.dateDue = dateDue;
    }

    /**
     * Gets the string representation for saving to file.
     *
     * @return the save string
     */
    @Override
    public String getSaveString() {
        return "DL|" + super.getSaveString() + DateTimeUtils.dateTimeToSaveString(dateDue) + "|";
    }

    /**
     * Gets the string representation of the deadline task.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeUtils.dateTimeToString(dateDue) + ")";
    }
}
