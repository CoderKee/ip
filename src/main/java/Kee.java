import java.util.Scanner;
public class Kee {
    private static final String indent = "     ";
    private static final String chatBorder = "     ____________________________________________________________";

    public static void main(String[] args) {
        startChat();
    }

    public static void greet() {
        System.out.println(chatBorder);
        System.out.println(indent + "Hello! I'm Kee");
        System.out.println(indent + "What can I do for you?");
        System.out.println(chatBorder);
    }

    public static void exit() {
        System.out.println(chatBorder);
        System.out.println(indent + "Bye. Hope to see you again soon!");
        System.out.println(chatBorder);
    }

    public static void echo(String msg) {
        System.out.println(chatBorder);
        System.out.println(indent + msg);
        System.out.println(chatBorder);
    }

    public static void startChat() {
        Scanner scanner = new Scanner(System.in);
        greet();
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                exit();
                break;
            }
            echo(input);
        }
        scanner.close();
    }
}