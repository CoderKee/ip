package kee;

import java.util.ArrayList;

import kee.task.Task;

/**
 * Handles responses to the user.
 */
public class UI {
    public static final String INDENT = "     ";
    private static final String CHAT_BORDER = "     ____________________________________________________________";

    /**
     * Constructs a new UI instance.
     */
    public UI() {}

    /**
     * Sends a greeting message when the chatbot starts.
     *
     * @return greeting message.
     */
    public String greet() {
        return "Hi! I'm Kee.\nWhat can I help you with? :D";
    }

    /**
     * Sends a farewell message when the chatbot ends.
     *
     * @return a farewell message.
     */
    public String exit() {
        return "Have a good day! ^.^";
    }

    /**
     * Returns the list of tasks with numbering.
     * If no tasks are present, a separate message is returned instead.
     *
     * @param tasks the list of tasks to display.
     * @return a message containing the list of tasks or no task message.
     */
    public String printTasks(ArrayList<Task> tasks) {
        assert tasks != null;
        if (!tasks.isEmpty()) {
            StringBuilder msg = new StringBuilder("Here are your tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                msg.append("\n").append(i + 1).append(". ").append(tasks.get(i).toString());
            }
            return msg.toString();
        } else {
            return "You have not added any tasks yet";
        }
    }

    /**
     * Returns a list of matching tasks received from the findTask method in TaskManager
     *
     * @param tasks the list of matching tasks
     * @return a message containing the list of found tasks
     */
    public String printFoundTasks(ArrayList<Task> tasks) {
        assert tasks != null;
        if (!tasks.isEmpty()) {
            StringBuilder msg = new StringBuilder("Here are the matching tasks I found:");
            for (int i = 0; i < tasks.size(); i++) {
                msg.append("\n").append(i + 1).append(". ").append(tasks.get(i).toString());
            }
            return msg.toString();
        } else {
            return "Seems like there's no matches";
        }

    }

    /**
     * Returns a message to acknowledge the addition of task
     *
     * @param task the added task
     * @param length the length of the task list
     * @return a message to acknowledge the addition of task
     */
    public String getAddedMessage(Task task, int length) {
        return "Okay, I've added:\n" + task.toString() + "\n" + "Now you've got " + length + " task(s)";
    }

    /**
     * Returns a message to acknowledge the deletion of task
     *
     * @param task the added task
     * @param length the length of the task list
     * @return a message to acknowledge the deletion of task
     */
    public String getDeleteMessage(Task task, int length) {
        return "Okay, I've removed:\n" + task.toString() + "\n" + "Now you've got " + length + " task(s)";
    }
}
