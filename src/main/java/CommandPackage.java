public class CommandPackage {
    private final Command cmd;
    private final String str;

    public CommandPackage(Command cmd, String str) {
        this.cmd = cmd;
        this.str = str;
    }

    public Command getCmd() {
        return cmd;
    }

    public String getStr() {
        return str;
    }

}
