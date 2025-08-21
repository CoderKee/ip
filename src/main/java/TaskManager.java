import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskManager {
    private ArrayList<Task> taskList;
    private final UI ui;

    public TaskManager(UI ui) {
        this.taskList = new ArrayList<>();
        this.ui = ui;
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

    public void addDeadline(String msg, LocalDateTime from) {
        Task newTask = new Deadline(msg, from);
        this.taskList.add(newTask);
        taskOutput(newTask);
    }

    public void addTodo(String msg) {
        Task newTask = new ToDo(msg);
        this.taskList.add(newTask);
        taskOutput(newTask);
    }

    public void addEvent(String msg, LocalDateTime from, LocalDateTime to) {
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
                output("Congratulations on completing:\n" + UI.INDENT + "  " + current.toString());
            } else {
                current.unmark();
                output("Ok, I've unmarked:\n" + UI.INDENT + "  " + current.toString());
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
        ui.print(msg);
    }

    public void taskOutput(Task task) {
        int length = this.taskList.size();
        output("Okay, I've added:\n" + UI.INDENT
                + "  " + task.toString() + "\n" + UI.INDENT
                + "Now you've got " + length + " tasks");
    }

    public void deleteOutput(Task task) {
        int length = this.taskList.size();
        output("Okay, I've removed:\n" + UI.INDENT
                + "  " + task.toString() + "\n" + UI.INDENT
                + "Now you've got " + length + " tasks");
    }

    public void getTasks() {
        ui.printTasks(this.taskList);
    }

    public ArrayList<Task> getList() {
        return taskList;
    }

    public void setTasks(ArrayList<Task> list) {
        this.taskList = list;
    }
}
