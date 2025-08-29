package kee.command;

import java.time.LocalDateTime;

/**
 * A class to represent additional information to be passed to TaskManager together with the Command.
 */
public class CommandPackage {
    private final Command cmd;
    private final String str;
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Constructs a CommandPackage containing only a command.
     *
     * @param cmd the command to encapsulate
     */
    public CommandPackage(Command cmd) {
        this.cmd = cmd;
        this.str = null;
        this.from = null;
        this.to = null;
    }

    /**
     * Constructs a CommandPackage containing command and task description.
     *
     * @param cmd the command to encapsulate
     * @param str the description of the task
     */
    public CommandPackage(Command cmd, String str) {
        this.cmd = cmd;
        this.str = str;
        this.from = null;
        this.to = null;
    }

    /**
     * Constructs a CommandPackage containing command, task description and deadline.
     *
     * @param cmd the command to encapsulate
     * @param str the description of the task
     * @param to the deadline of the task
     */
    public CommandPackage(Command cmd, String str, LocalDateTime to) {
        this.cmd = cmd;
        this.str = str;
        this.from = null;
        this.to = to;
    }

    /**
     * Constructs a CommandPackage containing command, task description, start time and end time.
     *
     * @param cmd the command to encapsulate
     * @param str the description of the task
     * @param from the start time of the task
     * @param to the end time of the task
     */
    public CommandPackage(Command cmd, String str, LocalDateTime from, LocalDateTime to) {
        this.cmd = cmd;
        this.str = str;
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the command associated with this package.
     *
     * @return the command as Command
     */
    public Command getCmd() {
        return cmd;
    }

    /**
     * Returns the description associated with this package.
     *
     * @return the description as String
     */
    public String getStr() {
        return str;
    }

    /**
     * Returns the start time associated with this package.
     *
     * @return the start time as LocalDateTime
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end time / deadline associated with this package.
     *
     * @return the end time as LocalDateTime
     */
    public LocalDateTime getTo() {
        return to;
    }

}
