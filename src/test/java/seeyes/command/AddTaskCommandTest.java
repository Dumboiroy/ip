package seeyes.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seeyes.storage.Storage;
import seeyes.task.Task;
import seeyes.task.TaskList;

public class AddTaskCommandTest {

    private TaskList taskList;
    private Storage storage;
    private AddTaskCommand addTaskCommand;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        storage = new Storage("./test-data.txt", taskList);
    }

    @Test
    public void execute_addValidTodoTask_success() {
        // Arrange
        Task todoTask = Task.of("Buy groceries");
        addTaskCommand = new AddTaskCommand(todoTask);
        addTaskCommand.setData(taskList, storage);

        // Act
        CommandResult result = addTaskCommand.execute();

        // Assert
        assertTrue(result.message.contains("Added:"));
        assertTrue(result.message.contains("Buy groceries"));
        assertTrue(result.message.contains("[T]"));
        assertEquals(1, taskList.size()); // Task should be added to list
        assertEquals(todoTask, taskList.getTaskByIndex(0)); // Verify the exact
                                                            // task was added
    }

    @Test
    public void execute_addValidDeadlineTask_success() {
        // Arrange
        Task deadlineTask = Task.of("Submit assignment", java.time.LocalDateTime.of(2024, 12, 31, 23, 59));
        addTaskCommand = new AddTaskCommand(deadlineTask);
        addTaskCommand.setData(taskList, storage);

        // Act
        CommandResult result = addTaskCommand.execute();

        // Assert
        assertTrue(result.message.contains("Added:"));
        assertTrue(result.message.contains("Submit assignment"));
        assertTrue(result.message.contains("[D]"));
        assertEquals(1, taskList.size()); // Task should be added to list
        assertEquals(deadlineTask, taskList.getTaskByIndex(0)); // Verify the
                                                                // exact task
                                                                // was added
    }

    @Test
    public void execute_addValidEventTask_success() {
        // Arrange
        Task eventTask = Task.of("Project Meeting", java.time.LocalDateTime.of(2024, 12, 31, 23, 59),
                java.time.LocalDateTime.of(2025, 1, 1, 00, 59));
        addTaskCommand = new AddTaskCommand(eventTask);
        addTaskCommand.setData(taskList, storage);

        // Act
        CommandResult result = addTaskCommand.execute();

        // Assert
        assertTrue(result.message.contains("Added:"));
        assertTrue(result.message.contains("Project Meeting"));
        assertTrue(result.message.contains("[E]"));
        assertEquals(1, taskList.size()); // Task should be added to list
        assertEquals(eventTask, taskList.getTaskByIndex(0)); // Verify the
                                                             // exact task
                                                             // was added
    }
}
