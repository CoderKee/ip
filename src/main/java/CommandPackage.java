public class CommandPackage {
    private final Command cmd;
    private final String str;
    private final String from;
    private final String to;

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

    public CommandPackage(Command cmd, String str, String to) {
        this.cmd = cmd;
        this.str = str;
        this.from = null;
        this.to = to;
    }
    
    public CommandPackage(Command cmd, String str, String from, String to) {
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

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

}
