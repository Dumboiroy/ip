package seeyes.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seeyes.exception.InvalidTaskTypeException;

public class TaskTest {
    @Test
    public void fromStringTestInvalidTaskType() {
        // Test invalid task type "XX"
        String taskString = "XX|0|Invalid task";

        InvalidTaskTypeException exception = assertThrows(InvalidTaskTypeException.class,
                () -> Task.fromString(taskString));

        // Verify the exception message contains the invalid task type
        assertTrue(exception.getMessage().contains("XX"));
        assertTrue(exception.getMessage().contains("does not exist"));

        // Test another invalid task type "ZZ"
        String taskString2 = "ZZ|1|Another invalid task";

        InvalidTaskTypeException exception2 = assertThrows(InvalidTaskTypeException.class,
                () -> Task.fromString(taskString2));

        assertTrue(exception2.getMessage().contains("ZZ"));
        assertTrue(exception2.getMessage().contains("does not exist"));
    }

    @Test
    public void fromStringTestTodo() {
        // Test unmarked todo task
        String taskString1 = "TD|0|Buy groceries";
        Task task1 = Task.fromString(taskString1);

        assertTrue(task1 instanceof TodoTask);
        assertFalse(task1.toString().contains("[X]")); // Not marked as done
        assertTrue(task1.toString().contains("Buy groceries"));

        // Test marked todo task
        String taskString2 = "TD|1|Read book";
        Task task2 = Task.fromString(taskString2);

        assertTrue(task2 instanceof TodoTask);
        assertTrue(task2.toString().contains("[X]")); // Marked as done
        assertTrue(task2.toString().contains("Read book"));

        // Test case insensitive - lowercase "td"
        String taskString3 = "td|0|Lowercase todo";
        Task task3 = Task.fromString(taskString3);

        assertTrue(task3 instanceof TodoTask);
        assertFalse(task3.toString().contains("[X]"));
        assertTrue(task3.toString().contains("Lowercase todo"));

        // Test task with special characters
        String taskString4 = "TD|0|Task with special chars!@#$%";
        Task task4 = Task.fromString(taskString4);

        assertTrue(task4 instanceof TodoTask);
        assertTrue(task4.toString().contains("Task with special chars!@#$%"));

        // Test boundary value for done status (non-1 should be false)
        String taskString5 = "TD|2|Task with status 2";
        Task task5 = Task.fromString(taskString5);

        assertTrue(task5 instanceof TodoTask);
        assertFalse(task5.toString().contains("[X]")); // Should be false for
                                                       // non-1 values
    }
}
