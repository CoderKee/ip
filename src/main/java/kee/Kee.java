package kee;

import kee.command.CommandPackage;

import kee.exception.KeeException;
import kee.exception.StorageException;
import kee.exception.DateException;

import kee.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

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
     * Greets the user by invoking the UI greeting.
     */
    public void greet() {
        this.ui.greet();
    }

    /**
     * Exits the chatbot.
     * Saves the current task list to storage and displays an exit message.
     * Invokes the UI bye message if successful, otherwise, returns a file saving error message.
     */
    public void exit() {
        try {
            this.storage.writeFile(this.manager.getList());
        } catch (StorageException e) {
            this.ui.print(e.getMessage());
        } finally {
            this.ui.exit();
        }
    }

    /**
     * Starts the chat session with the user.
     * Reads user input from the console, processes commands, and manages tasks based on the commands given.
     * The session continues until the user types "bye".
     */
    public void startChat() {
        Scanner scanner = new Scanner(System.in);
        greet();
        try {
            ArrayList<Task> tasks = this.storage.loadFile();
            this.manager.setTasks(tasks);
        } catch (StorageException e) {
            this.ui.print(e.getMessage());
        }
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                exit();
                break;
            }
            try {
                CommandPackage cmd = this.reader.read(input);
                this.manager.execute(cmd);
            } catch (KeeException | DateException e) {
                this.ui.print(e.getMessage());
            }
        }
        scanner.close();
    }
}