package Kee.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    LocalDateTime by;
    String formatted;

    /**
     * Constructs a new Deadline with the specified description and deadline.
     * The task is initially marked as not done.
     *
     * @param description the description of the task
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy h:mma");
        this.formatted = by.format(formatter);
    }

    /**
     * Returns a string representation of the Deadline task
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + formatted + ")";
    }

    /**
     * Returns a string representation of the Deadline task to be written to Storage
     * {@inheritDoc}
     */
    @Override
    public String toData() {
        return "D | " + super.toData() + " | " + formatted;
    }

    /**
     * Returns the deadline of the task
     *
     * @return the deadline as LocalDateTime
     */
    public LocalDateTime getBy() {
        return by;
    }
}
