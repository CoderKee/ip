import java.util.ArrayList;
import java.util.Scanner;

public class Kee {
    private static final String FILE_PATH = "./data/kee.txt";
    private final TaskManager manager;
    private final Reader reader;
    private final Storage storage;
    private final UI ui;

    public static void main(String[] args) {
        Kee chatBot = new Kee();
        chatBot.startChat();
    }

    public Kee() {
        this.reader = new Reader();
        this.storage = new Storage(FILE_PATH);
        this.ui = new UI();
        this.manager = new TaskManager(ui);
    }
    public void greet() {
        this.ui.greet();
    }

    public void exit() {
        try {
            this.storage.writeFile(this.manager.getList());
        } catch (StorageException e) {
            this.ui.print(e.getMessage());
        } finally {
            this.ui.exit();
        }
    }

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