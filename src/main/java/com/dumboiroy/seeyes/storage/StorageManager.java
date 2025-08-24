package com.dumboiroy.seeyes.storage;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.dumboiroy.seeyes.task.Task;

public class StorageManager {
    private final String filePath;

    public StorageManager(String path) {
        this.filePath = path;
    }

    public ArrayList<Task> load(String filepath) {
        // load file
        File file = new File(filePath);

        // parse the file and add tasks
        ArrayList<Task> taskList = new ArrayList<>();

        if (!file.exists()) {
            return taskList;
        }

        // return arraylist of tasks
        return null;
    }

    public void save(ArrayList<Task> taskList) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : taskList) {
                writer.write(task.getSaveString());
                writer.newLine();
            }
            System.out.println("list saved to: " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to save file at " + filePath);
        }
    }
}
