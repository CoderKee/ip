import java.util.ArrayList;

public class TaskManager {
    private final ArrayList<Task> taskList;
    private static final String indent = "     ";
    private static final String chatBorder = "     ____________________________________________________________";
    public TaskManager() {
        this.taskList = new ArrayList<>();
    }

    public void execute(CommandPackage cmd) throws KeeException {
        switch (cmd.getCmd()) {
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
            case DELETE:
                this.deleteEvent(cmd.getStr());
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

    public void markTask(String msg) throws KeeException {
        Task current = null;
        for (Task task : this.taskList) {
            if (task.getDescription().equals(msg)) {
                current = task;
                task.mark();
                break;
            }
        }
        if (current != null) {
            output("Congratulations on completing:\n" + indent + "  " + current.toString());
        } else {
            throw new KeeException("Oops! Task not found: " + msg);
        }
    }

    public void unmarkTask(String msg) throws KeeException{
        Task current = null;
        for (Task task : this.taskList) {
            if (task.getDescription().equals(msg)) {
                current = task;
                task.unmark();
                break;
            }
        }
        if (current != null) {
            output("Ok, I've unmarked:\n" + indent + "  " + current.toString());
        } else {
            throw new KeeException("Oops! Task not found: " + msg);
        }
    }

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

    public void deleteOutput(Task task) {
        int length = this.taskList.size();
        output("Okay, I've removed:\n" + indent
                + "  " + task.toString() + "\n" + indent
                + "Now you've got " + length + " tasks");
    }
    
    public void getTasks() {
        System.out.println(chatBorder);
        if (!this.taskList.isEmpty()) {
            System.out.println(indent + "Here are your tasks:");
            for (int i = 0; i < this.taskList.size(); i++) {
                System.out.println(indent + (i + 1) + ". " + this.taskList.get(i).toString());
            }
        } else {
            System.out.println(indent + "You have not added any tasks yet");
        }
        System.out.println(chatBorder);
    }
}
