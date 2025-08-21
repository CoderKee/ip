import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task{
    LocalDateTime from;
    LocalDateTime to;
    String fromFormatted;
    String toFormatted;
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy h:mma");
        this.fromFormatted = from.format(formatter);
        this.toFormatted = to.format(formatter);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + fromFormatted + " to: " + toFormatted + ")";
    }

    @Override
    public String toData() {
        return "E | " + super.toData() + " | " + fromFormatted + "-" + toFormatted;
    }
}
