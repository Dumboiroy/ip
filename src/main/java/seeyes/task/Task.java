package seeyes.task;

import java.time.LocalDateTime;

import seeyes.exception.InvalidTaskTypeException;
import seeyes.util.DateTimeUtils;

public abstract class Task {
    private String name;
    private boolean isDone;

    private enum TaskType {
        TODO("TD"), DEADLINE("DL"), EVENT("EV");

        private final String key;

        TaskType(String key) {
            this.key = key;
        }

        public static TaskType fromString(String taskString) throws InvalidTaskTypeException {
            for (TaskType t : TaskType.values()) {
                if (t.key.equalsIgnoreCase(taskString.split("\\|", 2)[0])) {
                    return t;
                }
            }
            throw new InvalidTaskTypeException("The task type " + taskString.split("\\|", 2)[0] + " does not exist.");
        }
    }

    protected Task(String name) {
        this.name = name;
        isDone = false;
    }

    protected Task(boolean isDone, String name) {
        this.name = name;
        this.isDone = isDone;
    }

    public String getSaveString() {
        return String.valueOf(isDone ? 1 : 0) + "|" + name + "|";
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
        return new TodoTask(false, name);
    }

    public static Task of(String name, LocalDateTime dateDue) {
        return new DeadlineTask(false, name, dateDue);
    }

    public static Task of(String name, LocalDateTime start, LocalDateTime end) {
        return new EventTask(false, name, start, end);
    }

    public static Task fromString(String taskString) {
        TaskType type = TaskType.fromString(taskString);
        String[] params = taskString.split("\\|");
        switch (type) {
        case TODO:
            return new TodoTask(params[1].equals("1"), params[2]);
        case DEADLINE:
            return new DeadlineTask(params[1].equals("1"), params[2], DateTimeUtils.parse(params[3]));
        case EVENT:
            return new EventTask(params[1].equals("1"), params[2], DateTimeUtils.parse(params[3]),
                    DateTimeUtils.parse(params[4]));
        default:
            throw new InvalidTaskTypeException("Invalid task type " + taskString.substring(0, 2) + ".");
        }

    }
}
