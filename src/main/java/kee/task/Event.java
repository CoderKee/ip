package kee.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A task to represent an event task.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;
    private String fromFormatted;
    private String toFormatted;

    /**
     * Constructs a new Event with the specified description, start time, and end time.
     * The start and end times are formatted to "d MMMM yyyy h:mma" format.
     * This task is initially marked as not done.
     *
     * @param description the description of the event
     * @param from the start time of the event
     * @param to the end time of the event
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy h:mma");
        this.fromFormatted = from.format(formatter);
        this.toFormatted = to.format(formatter);
    }

    /**
     * Returns the start time of the event.
     *
     * @return the start time as a LocalDateTime.
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end time of the event.
     *
     * @return the end time as a LocalDateTime.
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Returns a string representation of the Event task.
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + fromFormatted + " to: " + toFormatted + ")";
    }

    /**
     * Returns a string representation of th Event task to be written to Storage.
     * {@inheritDoc}
     */
    @Override
    public String toData() {
        return "E | " + super.toData() + " | " + fromFormatted + "-" + toFormatted;
    }
}
