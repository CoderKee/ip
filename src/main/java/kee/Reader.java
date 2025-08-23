package kee;

import kee.command.Command;
import kee.command.CommandPackage;

import kee.exception.DateException;
import kee.exception.KeeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Reader {

    /**
     * Parses a user input string, recognizes the command and converts it into a CommandPackage.
     * Throws exceptions if the input is invalid.
     *
     * @param msg the user input string
     * @return a CommandPackage representing the parsed command with additional parameters
     * @throws KeeException if the input is invalid or missing required parameters
     * @throws DateException if a date string is invalid or logically inconsistent
     */
    public CommandPackage read(String msg) throws KeeException, DateException {
        msg = msg.trim();
        int firstSpace = msg.indexOf(' ');
        String cmd = (firstSpace == -1) ? msg : msg.substring(0, firstSpace);
        String withoutCmd = (firstSpace == -1) ? "" : msg.substring(firstSpace + 1);
        switch (cmd.toLowerCase()) {
        case "list":
            return new CommandPackage(Command.LIST);
        case "mark":
            if (withoutCmd.isEmpty()) {
                throw new KeeException("Oops! You need to specify a task.");
            }
            return new CommandPackage(Command.MARK, withoutCmd);
        case "unmark":
            if (withoutCmd.isEmpty()) {
                throw new KeeException("Oops! You need to specify a task.");
            }
            return new CommandPackage(Command.UNMARK, withoutCmd);
        case "add":
            //FallThrough
        case "todo":
            if (withoutCmd.isEmpty()) {
                throw new KeeException("Oops! You need to specify a task.");
            }
            return new CommandPackage(Command.TODO, withoutCmd);
        case "deadline":
            String[] parts1 = withoutCmd.split(" /by ", 2);
            String description = parts1[0];
            if (description.isEmpty() || parts1.length == 1 || parts1[1].isEmpty()) {
                throw new KeeException("Oops! Did you forget to specify a task or deadline?");
            }
            LocalDateTime deadline = parseDate(parts1[1], "d/M/yyyy HH:mm");
            return new CommandPackage(Command.DEADLINE, description, deadline);
        case "event":
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
            LocalDateTime toTime = parseDate(eParts2[1], "d/M/yyyy HH:mm");
            if (fromTime.isAfter(toTime)) {
                throw new DateException("Oops! Your end time seems to be before your start time!");
            }
            return new CommandPackage(Command.EVENT, eDescription, fromTime, toTime);
        case "delete":
            if (withoutCmd.isEmpty()) {
                throw new KeeException("Oops! You need to specify a task.");
            }
            return new CommandPackage(Command.DELETE, withoutCmd);
        case "find":
            if (withoutCmd.isEmpty()) {
                throw new KeeException("Oops! You need to specify a task.");
            }
            return new CommandPackage(Command.FIND, withoutCmd);
        default:
            throw new KeeException("Oops! I do not recognise this command '" + cmd + "'");
        }
    }

    /**
     * Parses a date string into a LocalDateTime object using the specified format.
     *
     * @param date the date string to parse
     * @param format the expected date format
     * @return the parsed LocalDateTime object
     * @throws DateException if the given date cannot be parsed with the given format
     */
    public static LocalDateTime parseDate(String date, String format) throws DateException {
        try {
            DateTimeFormatter input = DateTimeFormatter.ofPattern(format);
            return LocalDateTime.parse(date, input);
        } catch (DateTimeParseException e) {
            throw new DateException("Oops! Try specifying the date in this format: " + format);
        }
    }
}
