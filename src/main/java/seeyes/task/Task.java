package seeyes.task;

import java.time.LocalDateTime;

import seeyes.exception.InvalidTaskTypeException;
import seeyes.util.DateTimeUtils;

/**
 * Abstract base class for all types of tasks.
 */
public abstract class Task {
    private String name;
    private boolean isDone;

    private enum TaskType {
        TODO("TD"), DEADLINE("DL"), EVENT("EV");

        private final String key;

        TaskType(String key) {
            this.key = key;
        }

        /**
         * Converts a string to a TaskType.
         *
         * @param taskString
         *            the string to convert
         * @return the corresponding TaskType
         * @throws InvalidTaskTypeException
         *             if the task type is invalid
         */
        public static TaskType fromString(String taskString)
                throws InvalidTaskTypeException {
            for (TaskType t : TaskType.values()) {
                if (t.key.equalsIgnoreCase(taskString.split("\\|", 2)[0])) {
                    return t;
                }
            }
            throw new InvalidTaskTypeException("The task type "
                    + taskString.split("\\|", 2)[0] + " does not exist.");
        }
    }

    /**
     * Creates a new task with the given name.
     *
     * @param name
     *            the name of the task
     */
    protected Task(String name) {
        this.name = name;
        isDone = false;
    }

    /**
     * Creates a new task with the given completion status and name.
     *
     * @param isDone
     *            whether the task is done
     * @param name
     *            the name of the task
     */
    protected Task(boolean isDone, String name) {
        this.name = name;
        this.isDone = isDone;
    }

    /**
     * Returns the name of the task.
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the string representation for saving to file.
     *
     * @return the save string
     */
    public String getSaveString() {
        return String.valueOf(isDone ? 1 : 0) + "|" + name + "|";
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Creates a todo task with the given name.
     *
     * @param name
     *            the name of the task
     * @return a new todo task
     */
    public static Task of(String name) {
        return new TodoTask(false, name);
    }

    /**
     * Creates a deadline task with the given name and due date.
     *
     * @param name
     *            the name of the task
     * @param dateDue
     *            the due date
     * @return a new deadline task
     */
    public static Task of(String name, LocalDateTime dateDue) {
        return new DeadlineTask(false, name, dateDue);
    }

    /**
     * Creates an event task with the given name, start time, and end time.
     *
     * @param name
     *            the name of the task
     * @param start
     *            the start time
     * @param end
     *            the end time
     * @return a new event task
     */
    public static Task of(String name, LocalDateTime start, LocalDateTime end) {
        return new EventTask(false, name, start, end);
    }

    /**
     * Creates a task from a saved string representation.
     *
     * @param taskString
     *            the string representation from file
     * @return the reconstructed task
     */
    public static Task getTaskFromString(String taskString) {
        TaskType type = TaskType.fromString(taskString);
        String[] params = taskString.split("\\|");
        switch (type) {
        case TODO:
            assert params.length == 3 : "Todo should have 3 params: type, isDone, name.";
            return new TodoTask(params[1].equals("1"), params[2]);
        case DEADLINE:
            assert params.length == 4 : "Deadline should have 4 params: type, isDone, name, deadline.";
            return new DeadlineTask(params[1].equals("1"), params[2],
                    DateTimeUtils.parse(params[3]));
        case EVENT:
            assert params.length == 5 : "Event should have 4 params: type, isDone, name, start, end.";
            return new EventTask(params[1].equals("1"), params[2],
                    DateTimeUtils.parse(params[3]),
                    DateTimeUtils.parse(params[4]));
        default:
            throw new InvalidTaskTypeException(
                    "Invalid task type " + taskString.substring(0, 2) + ".");
        }
    }

    /**
     * Checks if the task's name contains the specified query string.
     *
     * @param queryString
     *            the string to search for in the task's name
     * @return true if the task's name contains the query string, false otherwise
     */
    public boolean filterName(String queryString) {
        return name.contains(queryString);
    }

    /**
     * Gets the string representation of the task.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + name;
    }
}
