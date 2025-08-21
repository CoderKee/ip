import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    LocalDateTime by;
    String formatted;
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy h:mma");
        this.formatted = by.format(formatter);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + formatted + ")";
    }

    @Override
    public String toData() {
        return "D | " + super.toData() + " | " + formatted;
    }
}
