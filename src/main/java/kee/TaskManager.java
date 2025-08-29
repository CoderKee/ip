package kee;

import java.time.LocalDateTime;
import java.util.ArrayList;

import kee.command.CommandPackage;
import kee.exception.KeeException;
import kee.task.Deadline;
import kee.task.Event;
import kee.task.Task;
import kee.task.ToDo;


/**
 * A class to manage an array list of task
 */
public class TaskManager {
    private ArrayList<Task> taskList;
    private final UI ui;


    /**
     * Creates a new TaskManager with an empty task list.
     *
     * @param ui the UI class used for displaying messages.
     */
    public TaskManager(UI ui) {
        this.taskList = new ArrayList<>();
        this.ui = ui;
    }

    /**
     * Executes the given command by dispatching it to the appropriate handler based off Command type.
     * Returns a reply to acknowledge completion of command.
     *
     * @param cmd the command to execute.
     * @return message to acknowledge command.
     * @throws KeeException if the command cannot be executed.
     */
    public String execute(CommandPackage cmd) throws KeeException {
        switch (cmd.getCmd()) {
        case TODO:
            return this.addTodo(cmd.getStr());
        case MARK:
            return this.markTask(cmd.getStr(), true);
        case UNMARK:
            return this.markTask(cmd.getStr(), false);
        case LIST:
            return this.getTasks();
        case DEADLINE:
            return this.addDeadline(cmd.getStr(), cmd.getTo());
        case EVENT:
            return this.addEvent(cmd.getStr(), cmd.getFrom(), cmd.getTo());
        case DELETE:
            return this.deleteTask(cmd.getStr());
        case FIND:
            return this.findTask(cmd.getStr());
        default:
            throw new KeeException("Oops, I can't seem to understand this command");
        }
    }

    /**
     * Adds a Deadline task to the list. Returns a message of acknowledgement.
     *
     * @param msg  the description of the task.
     * @param end the deadline.
     * @return message to acknowledge completion.
     */
    public String addDeadline(String msg, LocalDateTime end) {
        Task newTask = new Deadline(msg, end);
        this.taskList.add(newTask);
        return outputTask(newTask);
    }

    /**
     * Adds a ToDo task to the list. Returns a message of acknowledgement.
     *
     * @param msg the description of the task.
     * @return message to acknowledge completion.
     */
    public String addTodo(String msg) {
        Task newTask = new ToDo(msg);
        this.taskList.add(newTask);
        return outputTask(newTask);
    }

    /**
     * Adds a Event task to the list. Returns a message of acknowledgement.
     *
     * @param msg the description of the task.
     * @param from the start time of the task.
     * @param to the end time of the task.
     * @return message to acknowledge completion.
     */
    public String addEvent(String msg, LocalDateTime from, LocalDateTime to) {
        Task newTask = new Event(msg, from, to);
        this.taskList.add(newTask);
        return outputTask(newTask);
    }

    /**
     * Marks or unmarks a task, identified by its index or description.
     * Returns a message of acknowledgement.
     *
     * @param msg  the task index (1-based) or description.
     * @param mark true to mark, false to unmark.
     * @return message to acknowledge completion.
     * @throws KeeException if the task cannot be found.
     */
    public String markTask(String msg, boolean mark) throws KeeException {
        Task current = null;
        try {
            int index = Integer.parseInt(msg);
            if (index > this.taskList.size()) {
                throw new KeeException("Oops! It seems that there is no task numbered: " + msg);
            }
            current = this.taskList.get(index - 1);
        } catch (NumberFormatException e) {
            int index = -1;
            for (int i = 0; i < this.taskList.size(); i++) {
                if (this.taskList.get(i).getDescription().equals(msg)) {
                    index = i;
                    current = this.taskList.get(i);
                    break;
                }
            }
            if (index == -1) {
                throw new KeeException("Oops! Task not found: " + msg);
            }
        } finally {
            if (current == null) {
                throw new KeeException("Oops! Task not found: " + msg);
            }
            if (mark) {
                current.mark();
                return "Congratulations on completing:\n" + current.toString();
            } else {
                current.unmark();
                return "Ok, I've unmarked:\n" + current.toString();
            }
        }
    }

    /**
     * Deletes a task identified by index or description.
     * Returns a message of acknowledgement.
     *
     * @param msg the task index (1-based) or description.
     * @return message to acknowledge completion.
     * @throws KeeException if the task cannot be found.
     */
    public String deleteTask(String msg) throws KeeException {
        try {
            int index = Integer.parseInt(msg);
            if (index > this.taskList.size()) {
                throw new KeeException("Oops! It seems that there is no task numbered: " + msg);
            }
            Task current = this.taskList.get(index - 1);
            this.taskList.remove(index - 1);
            return deleteOutput(current);
        } catch (NumberFormatException e) {
            int index = -1;
            Task current = null;
            for (int i = 0; i < this.taskList.size(); i++) {
                if (this.taskList.get(i).getDescription().equals(msg)) {
                    index = i;
                    current = this.taskList.get(i);
                    break;
                }
            }
            if (index != -1) {
                this.taskList.remove(index);
                return deleteOutput(current);
            } else {
                throw new KeeException("Oops! Task not found: " + msg);
            }
        }
    }

    /**
     * Iterates through task list to find task description matching the keyword in msg.
     * Returns a message of the found task.
     *
     * @param msg keyword of the task description.
     * @return message of found task.
     */
    public String findTask(String msg) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : this.taskList) {
            if (task.getDescription().contains(msg)) {
                tasks.add(task);
            }
        }
        return this.ui.printFoundTasks(tasks);
    }

    /**
     * Outputs a message after adding a task.
     *
     * @param task the task that was added.
     * @return message to acknowledge completion.
     */
    public String outputTask(Task task) {
        int length = this.taskList.size();
        return "Okay, I've added:\n" + task.toString() + "\n" + "Now you've got " + length + " task(s)";
    }

    /**
     * Outputs a message after deleting a task.
     *
     * @param task the task that was deleted.
     * @return message to acknowledge completion.
     */
    public String deleteOutput(Task task) {
        int length = this.taskList.size();
        return "Okay, I've removed:\n" + task.toString() + "\n" + "Now you've got " + length + " task(s)";
    }

    /**
     * Returns all tasks currently in the list as message.
     *
     * @return list of tasks.
     */
    public String getTasks() {
        return ui.printTasks(this.taskList);
    }

    /**
     * Returns the list of tasks.
     *
     * @return list of tasks as ArrayList of tasks.
     */
    public ArrayList<Task> getList() {
        return taskList;
    }

    /**
     * Sets the task list to a new list of tasks, replacing the old one.
     *
     * @param list the new list of tasks.
     */
    public void setTasks(ArrayList<Task> list) {
        this.taskList = list;
    }
}
