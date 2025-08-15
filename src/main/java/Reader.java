public class Reader {
    public CommandPackage read(String msg) {
        int firstSpace = msg.indexOf(' ');
        String cmd = (firstSpace == -1) ? msg : msg.substring(0, firstSpace);
        String task = (firstSpace == -1) ? msg : msg.substring(firstSpace + 1);
        switch (cmd.toLowerCase()) {
            case "list":
                return new CommandPackage(Command.LIST, msg);
            case "mark":
                return new CommandPackage(Command.MARK, task);
            case "unmark":
                return new CommandPackage(Command.UNMARK, task);
            default:
                return new CommandPackage(Command.ADD, msg);
        }
    }
}
