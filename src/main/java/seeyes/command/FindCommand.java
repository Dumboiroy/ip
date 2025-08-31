package seeyes.command;

import seeyes.exception.InvalidCommandException;

/**
 * Command to find tasks by their task description.
 */
public class FindCommand extends Command {
    private String queryString;

    /**
     * Constructs a FindCommand to search for tasks matching the given query string.
     *
     * @param queryString
     *            the search term used to filter tasks by description.
     */
    public FindCommand(String queryString) {
        this.queryString = queryString;
    }

    /**
     * Executes the find command to filter tasks by the query string.
     *
     * @return a CommandResult containing the filtered list of tasks
     * @throws InvalidCommandException
     *             if the query fails
     */
    @Override
    public CommandResult execute() {
        try {
            return new CommandResult("Here are the filtered tasks.", taskList.queryName(queryString));
        } catch (Exception e) {
            throw new InvalidCommandException("Unable to query.");
        }
    }
}
