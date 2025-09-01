package kee;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import kee.command.Command;
import kee.command.CommandPackage;
import kee.exception.DateException;
import kee.exception.KeeException;



/**
 * Reads and parses the user's input.
 */
public class Reader {

    /**
     * Parses a user input string, recognizes the command and converts it into a CommandPackage.
     * Throws exceptions if the input is invalid.
     *
     * @param msg the user input string.
     * @return a CommandPackage representing the parsed command with additional parameters.
     * @throws KeeException if the input is invalid or missing required parameters.
     * @throws DateException if a date string is invalid or logically inconsistent.
     */
    public CommandPackage read(String msg) throws KeeException, DateException {
        assert msg != null;
        msg = msg.trim();
        int firstSpace = msg.indexOf(' ');
        String cmd = (firstSpace == -1) ? msg : msg.substring(0, firstSpace);
        String withoutCmd = (firstSpace == -1) ? "" : msg.substring(firstSpace + 1);
        if (withoutCmd.isEmpty() && !cmd.equalsIgnoreCase("list")) {
            throw new KeeException("Oops! You need to specify a task.");
        }
        switch (cmd.toLowerCase()) {
        case "list":
            return new CommandPackage(Command.LIST);
        case "mark":
            return new CommandPackage(Command.MARK, withoutCmd);
        case "unmark":
            return new CommandPackage(Command.UNMARK, withoutCmd);
        case "add":
            //FallThrough
        case "todo":
            return new CommandPackage(Command.TODO, withoutCmd);
        case "deadline":
            return getDeadlinePackage(withoutCmd);
        case "event":
            return getEventPackage(withoutCmd);
            case "delete":
            return new CommandPackage(Command.DELETE, withoutCmd);
        case "find":
            return new CommandPackage(Command.FIND, withoutCmd);
        default:
            throw new KeeException("Oops! I do not recognise this command '" + cmd + "'");
        }
    }

    /**
     * Parses a date string into a LocalDateTime object using the specified format.
     *
     * @param date the date string to parse.
     * @param format the expected date format.
     * @return the parsed LocalDateTime object.
     * @throws DateException if the given date cannot be parsed with the given format.
     */
    public static LocalDateTime parseDate(String date, String format) throws DateException {
        assert date != null && !date.isEmpty();
        assert format != null && !format.isEmpty();
        try {
            DateTimeFormatter input = DateTimeFormatter.ofPattern(format);
            return LocalDateTime.parse(date, input);
        } catch (DateTimeParseException e) {
            throw new DateException("Oops! Try specifying the date in this format: " + format);
        }
    }

    /**
     * Parses the input string to extract event description, start time, and end time.
     * Validates input format and ensures the start time is before the end time.
     *
     * @param withoutCmd the raw input string excluding the command keyword.
     * @return a CommandPackage storing information of the event task.
     * @throws KeeException if the description, start time, or end time is missing.
     * @throws DateException if the start time is after the end time or if date cannot be parsed.
     */
    private static CommandPackage getEventPackage(String withoutCmd) throws KeeException, DateException {
        String[] eParts1 = withoutCmd.split(" /from ", 2);
        String eDescription = eParts1[0];
        if (eDescription.isEmpty() || eParts1.length == 1 || eParts1[1].isEmpty()) {
            throw new KeeException("Oops! Did you forget to specify a task or start time?");
        }
        String[] eParts2 = eParts1[1].split(" /to ", 2);
        String from = eParts2[0];
        if (from.isEmpty() || eParts2.length == 1 || eParts2[1].isEmpty()) {
            throw new KeeException("Oops! Did you forget to specify a start time or end time?");
        }
        String to = eParts2[1];
        LocalDateTime fromTime = parseDate(from, "d/M/yyyy HH:mm");
        LocalDateTime toTime = parseDate(to, "d/M/yyyy HH:mm");
        if (fromTime.isAfter(toTime)) {
            throw new DateException("Oops! Your end time seems to be before your start time!");
        }
        return new CommandPackage(Command.EVENT, eDescription, fromTime, toTime);
    }

    /**
     * Parses the input string to extract a deadline task description and due date.
     *
     * @param withoutCmd the raw input string excluding the command keyword.
     * @return a CommandPackage storing information of the deadline task.
     * @throws KeeException if the description or deadline is missing.
     * @throws DateException if the deadline date cannot be parsed correctly.
     */
    private static CommandPackage getDeadlinePackage(String withoutCmd) throws KeeException, DateException {
        String[] parts1 = withoutCmd.split(" /by ", 2);
        String description = parts1[0];
        if (description.isEmpty() || parts1.length == 1 || parts1[1].isEmpty()) {
            throw new KeeException("Oops! Did you forget to specify a task or deadline?");
        }
        LocalDateTime deadline = parseDate(parts1[1], "d/M/yyyy HH:mm");
        return new CommandPackage(Command.DEADLINE, description, deadline);
    }
}
