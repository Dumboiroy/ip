package com.dumboiroy.seeyes.storage;

import java.util.ArrayList;
import java.io.File;
import com.dumboiroy.seeyes.task.Task;

public class StorageManager {
    private String path;

    public StorageManager() {
        path = "";
    }

    public StorageManager(String path) {
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<Task> load(String filepath) {
        // load file
        File file = new File(path);

        // parse the file and add tasks

        // return arraylist of tasks
        return null;
    }

}
