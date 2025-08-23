package kee.command;

import java.time.LocalDateTime;

public class CommandPackage {
    private final Command cmd;
    private final String str;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public CommandPackage(Command cmd) {
        this.cmd = cmd;
        this.str = null;
        this.from = null;
        this.to = null;
    }

    public CommandPackage(Command cmd, String str) {
        this.cmd = cmd;
        this.str = str;
        this.from = null;
        this.to = null;
    }

    public CommandPackage(Command cmd, String str, LocalDateTime to) {
        this.cmd = cmd;
        this.str = str;
        this.from = null;
        this.to = to;
    }
    
    public CommandPackage(Command cmd, String str, LocalDateTime from, LocalDateTime to) {
        this.cmd = cmd;
        this.str = str;
        this.from = from;
        this.to = to;
    }

    public Command getCmd() {
        return cmd;
    }

    public String getStr() {
        return str;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

}
