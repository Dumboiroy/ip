package com.dumboiroy.seeyes.storage;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.dumboiroy.seeyes.exception.InvalidTaskTypeException;
import com.dumboiroy.seeyes.task.Task;

public class StorageManager {
    private final String filePath;

    public StorageManager(String path) {
        this.filePath = path;
    }

    public ArrayList<Task> load() {
        // load file
        File file = new File(filePath);

        // parse the file and add tasks
        ArrayList<Task> taskList = new ArrayList<>();
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return taskList;
        }
        String line = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                taskList.add(Task.fromString(line));
            }
            System.out.println("List from: " + filePath + " loaded.");
        } catch (IOException e) {
            System.out.println("Error while loading file: " + filePath);
        } catch (InvalidTaskTypeException e) {
            System.out.println("Error while parsing line: " + line + "\n" + e.getMessage());
        }

        // return arraylist of tasks
        return taskList;
    }

    public void save(ArrayList<Task> taskList) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : taskList) {
                writer.write(task.getSaveString());
                writer.newLine();
            }
            System.out.println("List at  " + filePath + " has been saved.");
        } catch (IOException e) {
            System.out.println("Failed to save file at " + filePath);
        }
    }
}
