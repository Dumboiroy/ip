package seeyes.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import seeyes.exception.InvalidTaskTypeException;
import seeyes.exception.StorageException;
import seeyes.task.Task;
import seeyes.task.TaskList;

/**
 * Handles loading and saving of tasks to file storage.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a storage instance with the specified file path.
     *
     * @param filePath
     *            the path to the storage file
     * @param taskList
     *            the task list (not used in current implementation)
     */
    public Storage(String filePath, TaskList taskList) {
        assert filePath != null : "filePath should not be null";
        assert !filePath.isEmpty() : "filePath should not be empty";
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return the loaded task list
     * @throws StorageException
     *             if loading fails
     */
    public TaskList load() throws StorageException {
        assert filePath != null : "filePath should not be null when loading";
        // load file
        File file = new File(filePath);

        // parse the file and add tasks
        TaskList taskList = new TaskList();
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return taskList;
        }
        String line = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                taskList.addTask(Task.getTaskFromString(line));
            }
        } catch (IOException e) {
            throw new StorageException("Error while loading file: " + filePath);
        } catch (InvalidTaskTypeException e) {
            throw new StorageException("Error while parsing line: " + line
                    + "\n" + e.getMessage());
        }

        // return arraylist of tasks
        return taskList;
    }

    /**
     * Saves the task list to the storage file.
     *
     * @param taskList
     *            the task list to save
     * @return a success message
     * @throws StorageException
     *             if saving fails
     */
    public String save(TaskList taskList) throws StorageException {
        assert taskList != null : "taskList should not be null when saving";
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(filePath))) {
            writeTasklistToFile(writer, taskList);
            return "List at " + filePath + " has been saved.";
        } catch (IOException e) {
            throw new StorageException("Unable to save list at " + filePath);
        }
    }

    /**
     * Writes a full tasklist to the provided BufferedWriter.
     *
     * @param writer
     * @param taskList
     * @throws IOException
     */
    public void writeTasklistToFile(BufferedWriter writer, TaskList taskList)
            throws IOException {
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.getTaskByIndex(i);
            assert task != null : "Task should not be null";
            writeTaskToFile(writer, task);
        }
    }

    /**
     * Writes a single task to the provided BufferedWriter. The task is written in its save string format, followed by a
     * newline.
     *
     * @param writer
     *            the BufferedWriter to write the task to
     * @param task
     *            the Task to write
     * @throws IOException
     *             if an I/O error occurs during writing
     */
    private void writeTaskToFile(BufferedWriter writer, Task task)
            throws IOException {
        writer.write(task.getSaveString());
        writer.newLine();
    }
}
