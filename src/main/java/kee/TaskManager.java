package kee;

import kee.command.CommandPackage;

import kee.exception.KeeException;

import kee.task.Task;
import kee.task.ToDo;
import kee.task.Deadline;
import kee.task.Event;

import java.time.LocalDateTime;

import java.util.ArrayList;

public class TaskManager {
    private ArrayList<Task> taskList;
    private final UI ui;


    /**
     * Creates a new TaskManager with an empty task list.
     *
     * @param ui the UI class used for displaying messages
     */
    public TaskManager(UI ui) {
        this.taskList = new ArrayList<>();
        this.ui = ui;
    }

    /**
     * Executes the given command by dispatching it to the appropriate handler based off Command type.
     *
     * @param cmd the command to execute
     * @throws KeeException if the command cannot be executed
     */
    public void execute(CommandPackage cmd) throws KeeException {
        switch (cmd.getCmd()) {
            case TODO:
                this.addTodo(cmd.getStr());
                break;
            case MARK:
                this.markTask(cmd.getStr(), true);
                break;
            case UNMARK:
                this.markTask(cmd.getStr(), false);
                break;
            case LIST:
                this.getTasks();
                break;
            case DEADLINE:
                this.addDeadline(cmd.getStr(), cmd.getTo());
                break;
            case EVENT:
                this.addEvent(cmd.getStr(), cmd.getFrom(), cmd.getTo());
                break;
            case DELETE:
                this.deleteEvent(cmd.getStr());
                break;
        }
    }

    /**
     * Adds a Deadline task to the list.
     *
     * @param msg  the description of the task
     * @param end the deadline
     */
    public void addDeadline(String msg, LocalDateTime end) {
        Task newTask = new Deadline(msg, end);
        this.taskList.add(newTask);
        outputTask(newTask);
    }

    /**
     * Adds a ToDo task to the list.
     *
     * @param msg the description of the task
     */
    public void addTodo(String msg) {
        Task newTask = new ToDo(msg);
        this.taskList.add(newTask);
        outputTask(newTask);
    }

    /**
     * Adds a Event task to the list.
     *
     * @param msg the description of the task
     * @param from the start time of the task
     * @param to the end time of the task
     */
    public void addEvent(String msg, LocalDateTime from, LocalDateTime to) {
        Task newTask = new Event(msg, from, to);
        this.taskList.add(newTask);
        outputTask(newTask);
    }

    /**
     * Marks or unmarks a task, identified by its index or description.
     *
     * @param msg  the task index (1-based) or description
     * @param mark true to mark, false to unmark
     * @throws KeeException if the task cannot be found
     */
    public void markTask(String msg, boolean mark) throws KeeException {
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
                output("Congratulations on completing:\n" + UI.INDENT + "  " + current.toString());
            } else {
                current.unmark();
                output("Ok, I've unmarked:\n" + UI.INDENT + "  " + current.toString());
            }
        }
    }

    /**
     * Deletes a task identified by index or description.
     *
     * @param msg the task index (1-based) or description
     * @throws KeeException if the task cannot be found
     */
    public void deleteEvent(String msg) throws KeeException {
        try {
            int index = Integer.parseInt(msg);
            if (index > this.taskList.size()) {
                throw new KeeException("Oops! It seems that there is no task numbered: " + msg);
            }
            Task current = this.taskList.get(index - 1);
            this.taskList.remove(index - 1);
            deleteOutput(current);
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
                deleteOutput(current);
            } else {
                throw new KeeException("Oops! Task not found: " + msg);
            }
        }
    }

    /**
     * Calls the UI class to print a message
     *
     * @param msg the message to be printed
     */
    public void output(String msg) {
        ui.print(msg);
    }

    /**
     * Outputs a message after adding a task
     *
     * @param task the task that was added
     */
    public void outputTask(Task task) {
        int length = this.taskList.size();
        output("Okay, I've added:\n" + UI.INDENT
                + "  " + task.toString() + "\n" + UI.INDENT
                + "Now you've got " + length + " task(s)");
    }

    /**
     * Outputs a message after deleting a task
     *
     * @param task the task that was deleted
     */
    public void deleteOutput(Task task) {
        int length = this.taskList.size();
        output("Okay, I've removed:\n" + UI.INDENT
                + "  " + task.toString() + "\n" + UI.INDENT
                + "Now you've got " + length + " task(s)");
    }

    /** Prints all tasks currently in the list by passing task list to UI. */
    public void getTasks() {
        ui.printTasks(this.taskList);
    }

    /**
     * Returns the list of tasks
     *
     * @return list of tasks as ArrayList<Task>
     */
    public ArrayList<Task> getList() {
        return taskList;
    }

    /**
     * Sets the task list to a new list of tasks, replacing the old one.
     *
     * @param list the new list of tasks
     */
    public void setTasks(ArrayList<Task> list) {
        this.taskList = list;
    }
}
