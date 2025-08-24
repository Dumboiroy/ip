package com.dumboiroy.seeyes.task;

import com.dumboiroy.seeyes.exception.InvalidTaskTypeException;

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
                if (t.key.equalsIgnoreCase(taskString.substring(0, 2))) {
                    return t;
                }
            }
            throw new InvalidTaskTypeException("The task type " + taskString.substring(0, 2) + " does not exist.");
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
        return new ToDoTask(false, name);
    }

    public static Task of(String name, String dateDue) {
        return new DeadlineTask(false, name, dateDue);
    }

    public static Task of(String name, String start, String end) {
        return new EventTask(false, name, start, end);
    }

    public static Task fromString(String taskString) {
        TaskType type = TaskType.fromString(taskString);
        String[] params = taskString.split("\\|");
        switch (type) {
        case TODO:
            return new ToDoTask(params[1].equals("1"), params[2]);
        case DEADLINE:
            return new DeadlineTask(params[1].equals("1"), params[2], params[3]);
        case EVENT:
            return new EventTask(params[1].equals("1"), params[2], params[3], params[4]);
        default:
            throw new InvalidTaskTypeException("Invalid task type " + taskString.substring(0, 2) + ".");
        }

    }
}
