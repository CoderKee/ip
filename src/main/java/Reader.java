public class Reader {
    public CommandPackage read(String msg) {
        int firstSpace = msg.indexOf(' ');
        String cmd = (firstSpace == -1) ? msg : msg.substring(0, firstSpace);
        String withoutCmd = (firstSpace == -1) ? msg : msg.substring(firstSpace + 1);
        switch (cmd.toLowerCase()) {
            case "list":
                return new CommandPackage(Command.LIST);
            case "mark":
                return new CommandPackage(Command.MARK, withoutCmd);
            case "unmark":
                return new CommandPackage(Command.UNMARK, withoutCmd);
            case "todo":
                return new CommandPackage(Command.TODO, withoutCmd);
            case "deadline":
                String[] parts1 = withoutCmd.split(" /by ", 2);
                System.out.println(parts1.length);
                String description = parts1[0];
                String deadline = parts1[1];
                return new CommandPackage(Command.DEADLINE, description, deadline);
            case "event":
                parts1 = withoutCmd.split(" /from ", 2);
                description = parts1[0];
                String[] parts2 = parts1[1].split(" /to ", 2);
                String from = parts2[0];
                String to = parts2[1];
                return new CommandPackage(Command.EVENT, description, from, to);
            default:
                System.out.println("Unknown command: " + cmd);
                return null;
        }
    }
}
