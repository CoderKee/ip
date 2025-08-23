package Kee.Task;

public class ToDo extends Task {

    /**
     * Constructs a new ToDo with the specified description.
     * The task is initially marked as not done.
     *
     * @param description the description of the task
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the ToDo task.
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }


    /**
     * Returns a string representation of the ToDo task to be written to Storage.
     * {@inheritDoc}
     */
    @Override
    public String toData() {
        return "T | " + super.toData();
    }
}
