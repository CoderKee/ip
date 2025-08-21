import java.util.ArrayList;

public class TaskManager {
    private final ArrayList<Task> taskList;
    private static final String INDENT = "     ";
    private static final String CHAT_BORDER = "     ____________________________________________________________";

    public TaskManager() {
        this.taskList = new ArrayList<>();
    }

    public TaskManager(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

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
                throw new KeeException("Oops! Task found: " + msg);
            }
            if (mark) {
                current.mark();
                output("Congratulations on completing:\n" + INDENT + "  " + current.toString());
            } else {
                current.unmark();
                output("Ok, I've unmarked:\n" + INDENT + "  " + current.toString());
            }
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
        System.out.println(CHAT_BORDER);
        System.out.println(INDENT + msg);
        System.out.println(CHAT_BORDER);
    }

    public void taskOutput(Task task) {
        int length = this.taskList.size();
        output("Okay, I've added:\n" + INDENT
                + "  " + task.toString() + "\n" + INDENT
                + "Now you've got " + length + " tasks");
    }

    public void deleteOutput(Task task) {
        int length = this.taskList.size();
        output("Okay, I've removed:\n" + INDENT
                + "  " + task.toString() + "\n" + INDENT
                + "Now you've got " + length + " tasks");
    }
    
    public void getTasks() {
        System.out.println(CHAT_BORDER);
        if (!this.taskList.isEmpty()) {
            System.out.println(INDENT + "Here are your tasks:");
            for (int i = 0; i < this.taskList.size(); i++) {
                System.out.println(INDENT + (i + 1) + ". " + this.taskList.get(i).toString());
            }
        } else {
            System.out.println(INDENT + "You have not added any tasks yet");
        }
        System.out.println(CHAT_BORDER);
    }

    public ArrayList<Task> getList() {
        return taskList;
    }
}
