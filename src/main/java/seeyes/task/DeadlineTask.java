package seeyes.task;

import java.time.LocalDateTime;

import seeyes.util.DateTimeUtils;

/**
 * Represents a task with a deadline.
 * This task type includes a due date/time by which the task should be completed.
 */
public class DeadlineTask extends Task {
    private LocalDateTime dateDue;

    protected DeadlineTask(boolean isDone, String name, LocalDateTime dateDue) {
        super(isDone, name);
        this.dateDue = dateDue;
    }

    @Override
    public String getSaveString() {
        return "DL|" + super.getSaveString() + DateTimeUtils.dateTimeToSaveString(dateDue) + "|";
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeUtils.dateTimeToString(dateDue) + ")";
    }
}
