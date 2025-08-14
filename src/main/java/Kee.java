import java.util.ArrayList;
import java.util.Scanner;

public class Kee {
    private static final String indent = "     ";
    private static final String chatBorder = "     ____________________________________________________________";
    private ToDo todo;

    public static void main(String[] args) {
        Kee chatBot = new Kee();
        chatBot.startChat();
    }

    public Kee() {
        this.todo = new ToDo();
    }
    public void greet() {
        System.out.println(chatBorder);
        System.out.println(indent + "Hello! I'm Kee");
        System.out.println(indent + "What can I do for you?");
        System.out.println(chatBorder);
    }

    public void exit() {
        System.out.println(chatBorder);
        System.out.println(indent + "Bye. Hope to see you again soon!");
        System.out.println(chatBorder);
    }

    public void addTask(String msg) {
        this.todo.addTask(msg);
        System.out.println(chatBorder);
        System.out.println(indent + "added: " + msg);
        System.out.println(chatBorder);
    }

    public void listTasks() {
        ArrayList<String> tasks = this.todo.getTasks();
        System.out.println(chatBorder);
        for (String task : tasks) {
            System.out.println(indent + task);
        }
        System.out.println(chatBorder);
    }
    public void startChat() {
        Scanner scanner = new Scanner(System.in);
        greet();
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                exit();
                break;
            }
            if (input.equals("list")) {
                listTasks();
            } else {
                addTask(input);
            }
        }
        scanner.close();
    }
}