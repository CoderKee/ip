import java.util.ArrayList;

public class TaskManager {
    private final ArrayList<Task> taskList;
    private static final String indent = "     ";
    private static final String chatBorder = "     ____________________________________________________________";
    public TaskManager() {
        this.taskList = new ArrayList<>();
    }

    public void execute(CommandPackage cmd) {
        switch (cmd.getCmd()) {
            case ADD:
            case TODO:
                this.addTodo(cmd.getStr());
                break;
            case MARK:
                this.markTask(cmd.getStr());
                break;
            case UNMARK:
                this.unmarkTask(cmd.getStr());
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
        }
    }

    public void addDeadline(String msg, String from) {
        Task newTask = new Deadline(msg, from);
        this.taskList.add(newTask);
        taskOutput(newTask);
    }

    public void addTodo(String msg) {
        Task newTask = new ToDo(msg);
        this.taskList.add(newTask);
        taskOutput(newTask);
    }

    public void addEvent(String msg, String from, String to) {
        Task newTask = new Event(msg, from, to);
        this.taskList.add(newTask);
        taskOutput(newTask);
    }

    public void markTask(String msg) {
        Task current = null;
        for (Task task : this.taskList) {
            if (task.getDescription().equals(msg)) {
                current = task;
                task.mark();
            }
        }
        if (current != null) {
            output("Congratulations on completing:\n" + indent + "  " + current.toString());
        } else {
            output("Oops! Task not found: " + msg);
        }
    }

    public void unmarkTask(String msg) {
        Task current = null;
        for (Task task : this.taskList) {
            if (task.getDescription().equals(msg)) {
                current = task;
                task.unmark();
            }
        }
        if (current != null) {
            output("Ok, I've unmarked:\n" + indent + "  " + current.toString());
        } else {
            output("Oops! Task not found: " + msg);
        }
    }

    public void output(String msg) {
        System.out.println(chatBorder);
        System.out.println(indent + msg);
        System.out.println(chatBorder);
    }

    public void taskOutput(Task task) {
        int length = this.taskList.size();
        output("Okay, I've added:\n" + indent
                + "  " + task.toString() + "\n" + indent
                + "Now you've got " + length + " tasks");
    }
    public void getTasks() {
        System.out.println(chatBorder);
        for (int i = 0; i < this.taskList.size(); i++) {
            System.out.println(indent + (i + 1) + ". " + this.taskList.get(i).toString());
        }
        System.out.println(chatBorder);
    }
}
