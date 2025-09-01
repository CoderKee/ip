package kee.task;

/**
 * Represents an abstract task with a description and completion status.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description the description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns whether the task is completed.
     *
     * @return true if the task is done, false otherwise.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Marks the task as completed.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns the description of the task.
     *
     * @return the task description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns a string representation of the task for the UI.
     *
     * @return the string representation of the task.
     */
    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + description;
    }

    /**
     * Returns a string representation to be written to Storage.
     *
     * @return the string representation of the task.
     */
    public String toData() {
        return (isDone ? "1" : "0") + " | " + description;
    }
}
