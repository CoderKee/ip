import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
            added = new Deadline(lines[2], lines[3]);
            break;
        case "E":
            if (lines.length <= 2) {
                throw new StorageException("Oops! This file is not what I expected.");
            }
            String[] time = lines[3].split("-");
            added = new Event(lines[2], time[0], time[1]);
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
