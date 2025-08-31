package seeyes.task;

import java.time.LocalDateTime;

import seeyes.util.DateTimeUtils;

/**
 * Represents a task that occurs during a specific time period.
 */
public class EventTask extends Task {
    private LocalDateTime start;
    private LocalDateTime end;

    /**
     * Creates an event task.
     * 
     * @param isDone whether the task is done
     * @param name the name of the task
     * @param start the start time
     * @param end the end time
     */
    protected EventTask(boolean isDone, String name, LocalDateTime start, LocalDateTime end) {
        super(isDone, name);
        this.start = start;
        this.end = end;
    }

    /**
     * Gets the string representation for saving to file.
     * 
     * @return the save string
     */
    @Override
    public String getSaveString() {
        return "EV|" + super.getSaveString() + DateTimeUtils.dateTimeToSaveString(start) + "|"
                + DateTimeUtils.dateTimeToSaveString(end) + "|";
    }

    /**
     * Gets the string representation of the event task.
     * 
     * @return the string representation
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateTimeUtils.dateTimeToString(start) + " to: "
                + DateTimeUtils.dateTimeToString(end) + ")";
    }
}
