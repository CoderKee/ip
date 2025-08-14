import java.util.ArrayList;

public class ToDo {
    private ArrayList<String> tasks;
    private int current = 1;

    public ToDo() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(String msg) {
        this.tasks.add(current + ". " + msg);
        current++;
    }

    public ArrayList<String> getTasks() {
        return tasks;
    }
}
