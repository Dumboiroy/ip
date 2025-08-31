package seeyes.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seeyes.storage.Storage;
import seeyes.task.Task;
import seeyes.task.TaskList;

public class FindCommandTest {
    private TaskList taskList;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        storage = new Storage("test", taskList);
        taskList.addTask(Task.of("read book"));
        taskList.addTask(Task.of("write report"));
        taskList.addTask(Task.of("buy groceries"));
    }

    @Test
    public void execute_findExistingTask_returnsMatchingTasks() {
        FindCommand findCommand = new FindCommand("book");
        findCommand.setData(taskList, storage);

        CommandResult result = findCommand.execute();

        assertTrue(result.getResultTasks().isPresent());
        assertEquals(1, result.getResultTasks().get().size());
        assertEquals("read book", result.getResultTasks().get().get(0).getName());
        assertEquals("Here are the matching tasks in your list:", result.message);
    }

    @Test
    public void execute_findNonExistingTask_returnsNoMatches() {
        FindCommand findCommand = new FindCommand("xyz");
        findCommand.setData(taskList, storage);

        CommandResult result = findCommand.execute();

        assertTrue(result.getResultTasks().isPresent());
        assertEquals(0, result.getResultTasks().get().size());
        assertEquals("No matching tasks found.", result.message);
    }

    @Test
    public void execute_findCaseInsensitive_returnsMatchingTasks() {
        FindCommand findCommand = new FindCommand("BOOK");
        findCommand.setData(taskList, storage);

        CommandResult result = findCommand.execute();

        assertTrue(result.getResultTasks().isPresent());
        assertEquals(1, result.getResultTasks().get().size());
        assertEquals("read book", result.getResultTasks().get().get(0).getName());
    }

    @Test
    public void execute_findPartialMatch_returnsMatchingTasks() {
        FindCommand findCommand = new FindCommand("re");
        findCommand.setData(taskList, storage);

        CommandResult result = findCommand.execute();

        assertTrue(result.getResultTasks().isPresent());
        assertEquals(2, result.getResultTasks().get().size());
        // Should find both "read book" and "write report"
    }
}