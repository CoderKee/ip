public class Reader {
    public CommandPackage read(String msg) throws KeeException {
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
            return new CommandPackage(Command.DEADLINE, description, parts1[1]);
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
            return new CommandPackage(Command.EVENT, eDescription, from, to);
        case "delete":
            if (withoutCmd.isEmpty()) {
                throw new KeeException("Oops! You need to specify a task.");
            }
            return new CommandPackage(Command.DELETE, withoutCmd);
        default:
            throw new KeeException("Oops! I do not recognise this command '" + cmd + "'");
        }
    }
}
