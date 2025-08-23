package kee;

import kee.task.Task;

import java.util.ArrayList;

public class UI {
    public static final String INDENT = "     ";
    private static final String CHAT_BORDER = "     ____________________________________________________________";

    public UI() {}

    public void greet() {
        System.out.println(CHAT_BORDER);
        System.out.println(INDENT + "Hi! I'm Kee.");
        System.out.println(INDENT + "What can I help you with? :D");
        System.out.println(CHAT_BORDER);
    }

    public void exit() {
        System.out.println(CHAT_BORDER);
        System.out.println(INDENT + "Have a good day! ^.^");
        System.out.println(CHAT_BORDER);
    }

    public void print(String s) {
        System.out.println(CHAT_BORDER);
        System.out.println(INDENT + s);
        System.out.println(CHAT_BORDER);
    }

    public void printTasks(ArrayList<Task> tasks) {
        System.out.println(CHAT_BORDER);
        if (!tasks.isEmpty()) {
            System.out.println(INDENT + "Here are your tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(INDENT + (i + 1) + ". " + tasks.get(i).toString());
            }
        } else {
            System.out.println(INDENT + "You have not added any tasks yet");
        }
        System.out.println(CHAT_BORDER);
    }
}
