import java.util.ArrayList;
import java.util.Scanner;

public class Kee {
    private static final String indent = "     ";
    private static final String chatBorder = "     ____________________________________________________________";
    private final TaskManager manager;
    private final Reader reader;

    public static void main(String[] args) {
        Kee chatBot = new Kee();
        chatBot.startChat();
    }

    public Kee() {
        this.manager = new TaskManager();
        this.reader = new Reader();
    }
    public void greet() {
        System.out.println(chatBorder);
        System.out.println(indent + "Hi! I'm Kee");
        System.out.println(indent + "What can I help you with? :D");
        System.out.println(chatBorder);
    }

    public void exit() {
        System.out.println(chatBorder);
        System.out.println(indent + "Have a good day! ^.^");
        System.out.println(chatBorder);
    }

    public void startChat() {
        Scanner scanner = new Scanner(System.in);
        greet();
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                exit();
                break;
            }
            CommandPackage cmd = this.reader.read(input);
            this.manager.execute(cmd);
        }
        scanner.close();
    }
}