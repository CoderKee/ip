public class Kee {
    private static final String chatBorder = "____________________________________________________________";
    public static void main(String[] args) {
        System.out.println(chatBorder);
        greet();
        System.out.println(chatBorder);
        exit();
        System.out.println(chatBorder);

    }

    private static void greet() {
        System.out.println("Hello! I'm Kee");
        System.out.println("What can I do for you?");
    }

    private static void exit() {
        System.out.println("Bye. Hope to see you again soon!");
    }
}