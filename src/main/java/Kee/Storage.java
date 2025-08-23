package Kee;

import Kee.Exception.DateException;
import Kee.Exception.StorageException;
import Kee.Task.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String path;

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param path the file path for storing task data
     */
    public Storage(String path) {
        this.path = path;
    }


    /**
     * Loads tasks from the file specified by the field path.
     * If the file does not exist, it will attempt to create a new file.
     *
     * @return an ArrayList of tasks loaded from the file
     * @throws StorageException if the file content is invalid or cannot be read (i.e. corrupted)
     */
    public ArrayList<Task> loadFile() throws StorageException {
        File file = new File(path);
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task newTask = this.assignTask(line);
                tasks.add(newTask);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            this.createNew();
        }
        return tasks;
    }

    /**
     * Converts a line from the file into a Task object.
     *
     * @param line a single line from the task file
     * @return the corresponding Task object made from that line
     * @throws StorageException if the line is invalid or cannot be parsed (i.e. corrupted)
     */
    public Task assignTask(String line) throws StorageException {
        String[] lines = line.split(" \\| ");
        if (lines.length <= 1) {
            throw new StorageException("Oops! This file is not what I expected.");
        }
        Task added = null;
        switch (lines[0]) {
        case "T":
            if (lines.length != 3) {
                throw new StorageException("Oops! This file is not what I expected.");
            }
            added = new ToDo(lines[2]);
            break;
        case "D":
            if (lines.length != 4) {
                throw new StorageException("Oops! This file is not what I expected.");
            }
            try {
                LocalDateTime deadline = Reader.parseDate(lines[3], "d MMMM yyyy h:mma");
                added = new Deadline(lines[2], deadline);
            } catch (DateException e) {
                throw new StorageException("Oops! This file is not what I expected.");
            }
            break;
        case "E":
            if (lines.length != 4) {
                throw new StorageException("Oops! This file is not what I expected.");
            }
            try {
                String[] time = lines[3].split("-");
                if (time.length != 2) {
                    throw new StorageException("Oops! This file is not what I expected.");
                }
                LocalDateTime from = Reader.parseDate(time[0], "d MMMM yyyy h:mma");
                LocalDateTime to = Reader.parseDate(time[1], "d MMMM yyyy h:mma");
                added = new Event(lines[2], from, to);
            } catch (DateException e) {
                throw new StorageException("Oops! This file is not what I expected.");
            }
            break;
        default:
            throw new StorageException("Oops! This file is not what I expected.");
        }
        if (!lines[1].equals("1") && !lines[1].equals("0")) {
            throw new StorageException("Oops! This file is not what I expected.");
        }
        if (lines[1].equals("1")) {
            added.mark();
        }
        return added;
    }

    /**
     * Creates a new empty file at the specified path.
     * Also creates parent directories if they do not exist.
     *
     * @throws StorageException if the file or directories cannot be created
     */
    public void createNew() throws StorageException {
        try {
            File file = new File(path);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Oops! I can't seem to create a new file.");
        }
    }

    /**
     * Writes the given list of tasks to the file at the specified path.
     * Each task is written in its data string format.
     *
     * @param tasks the ArrayList of tasks to save
     * @throws StorageException if the file cannot be written
     */
    public void writeFile(ArrayList<Task> tasks) throws StorageException {
        File file = new File(path);
        try {
            FileWriter writer = new FileWriter(path);
            for (Task task : tasks) {
                writer.write(task.toData());
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new StorageException("Oops! I can't seem to save your data.");
        }
    }
}
