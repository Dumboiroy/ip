package seeyes.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seeyes.command.CommandResult;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    @Test
    public void createListCommandResult_emptyList_returnsEmptyMessage() {
        CommandResult result = taskList.createListCommandResult();
        
        assertEquals("list is empty! add your first item with 'todo [item]'.", result.message);
        assertTrue(result.getTaskList().isEmpty());
        assertTrue(result.getResultTasks().isEmpty());
    }

    @Test
    public void createListCommandResult_withTasks_returnsListWithCount() {
        Task task1 = Task.of("Test task 1");
        Task task2 = Task.of("Test task 2");
        taskList.addTask(task1);
        taskList.addTask(task2);

        CommandResult result = taskList.createListCommandResult();
        
        assertTrue(result.message.contains("You have 2 items in your list"));
        // The constructor with TaskList sets the taskList field, not resultTasks
        assertTrue(result.getTaskList().isPresent());
        assertEquals(2, result.getTaskList().get().size());
    }

    @Test
    public void addTaskWithResult_validTask_returnsSuccessMessage() {
        Task task = Task.of("Test task");
        
        CommandResult result = taskList.addTaskWithResult(task);
        
        assertTrue(result.message.contains("Added: "));
        assertTrue(result.message.contains("Test task"));
        assertEquals(1, taskList.size());
    }
}