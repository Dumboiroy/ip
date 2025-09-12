package seeyes.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seeyes.task.Task;
import seeyes.task.TaskList;

class StorageTest {

    private static final String TEST_FILE_PATH = "./test_data/test_data.txt";
    private Storage storage;
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
        storage = new Storage(TEST_FILE_PATH, taskList);
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
    }

    @Test
    void testSaveAndLoad() throws Exception {
        // Add tasks
        taskList.addTask(Task.of("Test Task 1"));
        taskList.addTask(Task.of("Test Task 2"));

        // Save tasks
        String saveMsg = storage.save(taskList);
        assertTrue(saveMsg.contains(TEST_FILE_PATH));

        // Load tasks
        TaskList loadedList = storage.load();
        assertEquals(2, loadedList.size());
        assertEquals("Test Task 1", loadedList.getTaskByIndex(0).getName());
        assertEquals("Test Task 2", loadedList.getTaskByIndex(1).getName());
    }

    @Test
    void testListIsEmptyOnNewFile() throws Exception {
        // Load from a non-existent file
        TaskList loadedList = storage.load();
        assertEquals(0, loadedList.size());
    }
}
