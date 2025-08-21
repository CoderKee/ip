import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String path;

    public Storage(String path) {
        this.path = path;
    }

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

    public Task assignTask(String line) throws StorageException {
        String[] lines = line.split(" \\| ");
        if (lines.length <= 1) {
            throw new StorageException("Oops! This file is not what I expected.");
        }
        Task added = null;
        switch (lines[0]) {
        case "T":
            added = new ToDo(lines[2]);
            break;
        case "D":
            if (lines.length <= 2) {
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
            if (lines.length <= 2) {
                throw new StorageException("Oops! This file is not what I expected.");
            }
            try {
                String[] time = lines[3].split("-");
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
        if (lines[1].equals("1")) {
            added.mark();
        }
        return added;
    }

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
