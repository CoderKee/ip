import java.util.ArrayList;
import java.util.Scanner;

public class Kee {
    private static final String INDENT = "     ";
    private static final String CHAT_BORDER = "     ____________________________________________________________";
    private static final String FILE_PATH = "./data/kee.txt";
    private TaskManager manager;
    private final Reader reader;
    private final Storage storage;

    public static void main(String[] args) {
        Kee chatBot = new Kee();
        chatBot.startChat();
    }

    public Kee() {
        this.manager = new TaskManager();
        this.reader = new Reader();
        this.storage = new Storage(FILE_PATH);
    }
    public void greet() {
        System.out.println(CHAT_BORDER);
        System.out.println(INDENT + "Hi! I'm Kee");
        System.out.println(INDENT + "What can I help you with? :D");
        System.out.println(CHAT_BORDER);
    }

    public void exit() {
        try {
            this.storage.writeFile(this.manager.getList());
        } catch (StorageException e) {
            System.out.println(CHAT_BORDER);
            System.out.println(INDENT + e.getMessage());
            System.out.println(CHAT_BORDER);
        } finally {
            System.out.println(CHAT_BORDER);
            System.out.println(INDENT + "Have a good day! ^.^");
            System.out.println(CHAT_BORDER);
        }
    }

    public void startChat() {
        Scanner scanner = new Scanner(System.in);
        greet();
        try {
            ArrayList<Task> tasks = this.storage.loadFile();
            this.manager = new TaskManager(tasks);
        } catch (StorageException e) {
            System.out.println(CHAT_BORDER);
            System.out.println(INDENT + e.getMessage());
            System.out.println(CHAT_BORDER);
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
                System.out.println(CHAT_BORDER);
                System.out.println(INDENT + e.getMessage());
                System.out.println(CHAT_BORDER);
            }
        }
        scanner.close();
    }
}