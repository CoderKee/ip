package kee;

import kee.command.CommandPackage;

import kee.exception.KeeException;
import kee.exception.StorageException;
import kee.exception.DateException;

import kee.task.Task;

import java.util.ArrayList;

/**
 * A class to initialise the messaging capability of the chatbot.
 */
public class Kee {
    private static final String FILE_PATH = "./data/kee.txt";
    private final TaskManager manager;
    private final Reader reader;
    private final Storage storage;
    private final UI ui;

    /**
     * The main entry point for the Kee chatbot application.
     */
    public static void main(String[] args) {
        Kee chatBot = new Kee();
        chatBot.startChat();
    }

    /**
     * Constructs a new Kee chatbot instance.
     * Initializes the reader, storage, UI, and task manager components.
     */
    public Kee() {
        this.reader = new Reader();
        this.storage = new Storage(FILE_PATH);
        this.ui = new UI();
        this.manager = new TaskManager(ui);
    }

    /**
     * Exits the chatbot.
     * Saves the current task list to storage and displays an exit message.
     * Invokes the UI bye message if successful, otherwise, returns a file saving error message.
     */
    public String exit() {
        try {
            this.storage.writeFile(this.manager.getList());
        } catch (StorageException e) {
            return e.getMessage();
        }
        return this.ui.exit();
    }

    /**
     * Loads the file from storage and greets the user.
     * If file is corrupted, return an error message instead.
     *
     * @return a greeting message or an error message.
     */
    public String startChat() {
        try {
            ArrayList<Task> tasks = this.storage.loadFile();
            this.manager.setTasks(tasks);
        } catch (StorageException e) {
            return e.getMessage();
        }
        return this.ui.greet();
    }

    /**
     * Returns a String response to user based off given inputs
     *
     * @param input user input
     * @return response to input or error message
     */
    public String getResponse(String input) {
        if (input.equalsIgnoreCase("bye")) {
            return this.exit();
        } else {
            try {
                CommandPackage cmd = this.reader.read(input);
                return this.manager.execute(cmd);
            } catch (KeeException | DateException e) {
                return e.getMessage();
            }
        }
    }
}