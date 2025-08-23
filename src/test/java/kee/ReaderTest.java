package kee;

import kee.exception.DateException;
import kee.exception.KeeException;

import kee.command.CommandPackage;
import kee.command.Command;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReaderTest {


    @Test
    void read_listCommand_returnsListCommandPackage() throws KeeException, DateException {
        Reader reader = new Reader();
        CommandPackage cmd = reader.read("list");
        assertEquals(Command.LIST, cmd.getCmd());
    }

    @Test
    void read_markCommand_returnsMarkCommandPackage() throws KeeException, DateException {
        Reader reader = new Reader();
        CommandPackage cmd = reader.read("mark 1");
        assertEquals(Command.MARK, cmd.getCmd());
        assertEquals("1", cmd.getStr());
    }

    @Test
    void read_unmarkCommand_returnsUnmarkCommandPackage() throws KeeException, DateException {
        Reader reader = new Reader();
        CommandPackage cmd = reader.read("unmark 2");
        assertEquals(Command.UNMARK, cmd.getCmd());
        assertEquals("2", cmd.getStr());
    }

    @Test
    void read_todoCommand_returnsTodoCommandPackage() throws KeeException, DateException {
        Reader reader = new Reader();
        CommandPackage cmd = reader.read("todo read book");
        assertEquals(Command.TODO, cmd.getCmd());
        assertEquals("read book", cmd.getStr());
    }

    @Test
    void read_deadlineCommand_returnsDeadlineCommandPackage() throws KeeException, DateException {
        Reader reader = new Reader();
        CommandPackage cmd = reader.read("deadline submit report /by 22/8/2025 10:00");
        assertEquals(Command.DEADLINE, cmd.getCmd());
        assertEquals("submit report", cmd.getStr());

        LocalDateTime expected = LocalDateTime.of(2025, 8, 22, 10, 00);
        assertEquals(expected, cmd.getTo());
    }

    @Test
    void read_eventCommand_returnsEventCommandPackage() throws KeeException, DateException {
        Reader reader = new Reader();
        CommandPackage cmd = reader.read("event lecture /from 22/8/2025 14:00 /to 22/8/2025 16:00");
        assertEquals(Command.EVENT, cmd.getCmd());
        assertEquals("lecture", cmd.getStr());

        LocalDateTime expectedFrom = LocalDateTime.of(2025, 8, 22, 14, 00);
        LocalDateTime expectedTo = LocalDateTime.of(2025, 8, 22, 16, 00);
        assertEquals(expectedFrom, cmd.getFrom());
        assertEquals(expectedTo, cmd.getTo());
    }

    @Test
    void read_deleteCommand_returnsDeleteCommandPackage() throws KeeException, DateException {
        Reader reader = new Reader();
        CommandPackage cmd = reader.read("delete 1");
        assertEquals(Command.DELETE, cmd.getCmd());
        assertEquals("1", cmd.getStr());
    }

    @Test
    void read_unknownCommand_throwsKeeException() {
        Reader reader = new Reader();
        assertThrows(KeeException.class, () -> reader.read("tod"));
    }

    @Test
    void read_markWithoutArgument_throwsKeeException() {
        Reader reader = new Reader();
        assertThrows(KeeException.class, () -> reader.read("mark"));
    }

    @Test
    void read_deadlineMissingDate_throwsKeeException() {
        Reader reader = new Reader();
        assertThrows(KeeException.class, () -> reader.read("deadline report"));
    }

    @Test
    void read_eventEndBeforeStart_throwsDateException() {
        Reader reader = new Reader();
        assertThrows(DateException.class, () -> reader.read(
                "event meeting /from 22/8/2025 16:00 /to 22/8/2025 14:00"));
    }

    @Test
    void read_taskMissingDescription_throwsKeeException() {
        Reader reader = new Reader();
        assertThrows(KeeException.class, () -> reader.read("todo"));
    }
}
