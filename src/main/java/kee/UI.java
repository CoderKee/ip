package kee;

import kee.task.Task;

import java.util.ArrayList;

public class UI {
    public static final String INDENT = "     ";
    private static final String CHAT_BORDER = "     ____________________________________________________________";

    /**
     * Constructs a new UI instance.
     */
    public UI() {}

    /**
     * Prints a greeting message when the chatbot starts.
     */
    public void greet() {
        System.out.println(CHAT_BORDER);
        System.out.println(INDENT + "Hi! I'm Kee.");
        System.out.println(INDENT + "What can I help you with? :D");
        System.out.println(CHAT_BORDER);
    }

    /**
     * Prints a farewell message when the chatbot ends.
     */
    public void exit() {
        System.out.println(CHAT_BORDER);
        System.out.println(INDENT + "Have a good day! ^.^");
        System.out.println(CHAT_BORDER);
    }

    /**
     * Prints a single message enclosed in a chat border.
     *
     * @param s the message to print
     */
    public void print(String s) {
        System.out.println(CHAT_BORDER);
        System.out.println(INDENT + s);
        System.out.println(CHAT_BORDER);
    }

    /**
     * Prints the list of tasks with numbering.
     * If no tasks are present, a separate message is displayed instead.
     *
     * @param tasks the list of tasks to display
     */
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

    /**
     * Prints a list of matching tasks received from the findTask method in TaskManager
     *
     * @param tasks the list of matching tasks
     */
    public void printFoundTasks(ArrayList<Task> tasks) {
        System.out.println(CHAT_BORDER);
        if (!tasks.isEmpty()) {
            System.out.println(INDENT + "Here are the matching tasks I found:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(INDENT + (i + 1) + ". " + tasks.get(i).toString());
            }
        } else {
            System.out.println(INDENT + "Seems like there's no matches");
        }
        System.out.println(CHAT_BORDER);
    }
}
