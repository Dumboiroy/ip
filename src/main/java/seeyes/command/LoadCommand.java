package seeyes.command;

import seeyes.exception.CommandFailedException;
import seeyes.exception.StorageException;
import seeyes.task.TaskList;

public class LoadCommand extends Command {
    @Override
    public CommandResult execute() throws CommandFailedException {
        try {
            TaskList loadedTaskList = storage.load();
            // Clear current taskList and add all tasks from loaded list
            while (taskList.size() > 0) {
                taskList.removeTaskByIndex(0);
            }
            for (int i = 0; i < loadedTaskList.size(); i++) {
                taskList.addTask(loadedTaskList.getTaskByIndex(i));
            }
            return new CommandResult("Load Success.", taskList.getTaskList());
        } catch (StorageException e) {
            throw new CommandFailedException(e.getMessage());
        }
    }
}
