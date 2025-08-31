package seeyes.task;

import java.time.LocalDateTime;

import seeyes.util.DateTimeUtils;

/**
 * Represents an event task with a start and end time.
 * This task type includes both a start time and end time for the event.
 */
public class EventTask extends Task {
    private LocalDateTime start;
    private LocalDateTime end;

    protected EventTask(boolean isDone, String name, LocalDateTime start, LocalDateTime end) {
        super(isDone, name);
        this.start = start;
        this.end = end;
    }

    @Override
    public String getSaveString() {
        return "EV|" + super.getSaveString() + DateTimeUtils.dateTimeToSaveString(start) + "|"
                + DateTimeUtils.dateTimeToSaveString(end) + "|";
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateTimeUtils.dateTimeToString(start) + " to: "
                + DateTimeUtils.dateTimeToString(end) + ")";
    }
}
