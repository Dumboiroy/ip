package com.dumboiroy.seeyes.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.dumboiroy.seeyes.exception.InvalidTaskTypeException;
import com.dumboiroy.seeyes.exception.StorageException;
import com.dumboiroy.seeyes.task.Task;
import com.dumboiroy.seeyes.task.TaskList;

public class Storage {
    private final String filePath;

    public Storage(String filePath, TaskList taskList) {
        this.filePath = filePath;
    }

    public TaskList load() throws StorageException {
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
                taskList.addTask(Task.fromString(line));
            }
            // System.out.println("List from: " + filePath + " loaded.");
        } catch (IOException e) {
            throw new StorageException("Error while loading file: " + filePath);
        } catch (InvalidTaskTypeException e) {
            throw new StorageException("Error while parsing line: " + line + "\n" + e.getMessage());
        }

        // return arraylist of tasks
        return taskList;
    }

    public String save(TaskList taskList) throws StorageException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < taskList.size(); i++) {
                writer.write(taskList.getTaskByIndex(i).getSaveString());
                writer.newLine();
            }
            return "List at " + filePath + " has been saved.";
        } catch (IOException e) {
            throw new StorageException("Unable to save list at " + filePath);
        }
    }
}
