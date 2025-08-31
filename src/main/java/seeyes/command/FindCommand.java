package seeyes.command;

import java.util.List;
import java.util.stream.Collectors;

import seeyes.task.Task;

public class FindCommand extends Command {
    private String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public CommandResult execute() {
        List<Task> matchingTasks = taskList.getTaskList().stream()
                .filter(task -> task.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        String message = matchingTasks.isEmpty() 
                ? "No matching tasks found." 
                : "Here are the matching tasks in your list:";

        return new CommandResult(message, matchingTasks);
    }
}