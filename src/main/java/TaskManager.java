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
                this.addTask(cmd.getStr());
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
        }
    }

    public void addTask(String msg) {
        this.taskList.add(new ToDo(msg));
        System.out.println(chatBorder);
        System.out.println(indent + "added: " + msg);
        System.out.println(chatBorder);
    }

    public void markTask(String msg) {
        Task current = null;
        for (Task task : this.taskList) {
            if (task.getDescription().equals(msg)) {
                current = task;
                task.mark();
            }
        }
        System.out.println(chatBorder);
        if (current != null) {
            System.out.println(indent + "Congratulations on completing: ");
            System.out.println(indent + "  " + current.toString());
        } else {
            System.out.println(indent + "Oops! Task not found: " + msg);
        }
        System.out.println(chatBorder);
    }

    public void unmarkTask(String msg) {
        Task current = null;
        for (Task task : this.taskList) {
            if (task.getDescription().equals(msg)) {
                current = task;
                task.unmark();
            }
        }
        System.out.println(chatBorder);
        if (current != null) {
            System.out.println(indent + "Ok, I've unmarked: ");
            System.out.println(indent + "  " + current.toString());
        } else {
            System.out.println(indent + "Oops! Task not found: " + msg);
        }
        System.out.println(chatBorder);
    }

    public void getTasks() {
        System.out.println(chatBorder);
        for (int i = 0; i < this.taskList.size(); i++) {
            System.out.println(indent + (i + 1) + ". " + this.taskList.get(i).toString());
        }
        System.out.println(chatBorder);
    }
}
