package seeyes.task;

import java.time.LocalDateTime;

import seeyes.util.DateTimeUtils;

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
