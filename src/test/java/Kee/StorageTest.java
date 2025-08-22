package Kee;

import Kee.Exception.StorageException;
import Kee.Task.Event;
import Kee.Task.Task;
import Kee.Task.Deadline;
import Kee.Task.ToDo;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {
    Storage storage = new Storage("dummy");

    @Test
    void assignTask_validToDo_createsToDoTask() throws StorageException {
        String line = "T | 0 | read book";
        Task task = storage.assignTask(line);

        assertTrue(task instanceof ToDo);
        assertEquals("read book", task.getDescription());
        assertFalse(task.isDone());
    }

    @Test
    void assignTask_validDeadline_createsDeadline() throws StorageException {
        String line = "D | 0 | code | 22 August 2025 10:00am";
        Task task = storage.assignTask(line);

        assertTrue(task instanceof Deadline);
        assertEquals("code", task.getDescription());
        assertFalse(task.isDone());

        Deadline d = (Deadline) task;
        LocalDateTime expected = LocalDateTime.of(2025, 8, 22, 10, 00);
        assertEquals(expected, d.getBy());
    }

    @Test
    void assignTask_validMarkedEvent_createsMarkedEvent() throws StorageException {
        String line = "E | 1 | lecture | 22 August 2025 2:00pm-22 August 2025 4:00pm";
        Task task = storage.assignTask(line);

        assertTrue(task instanceof Event);
        assertEquals("lecture", task.getDescription());
        assertTrue(task.isDone());

        Event e = (Event) task;
        LocalDateTime expectedFrom = LocalDateTime.of(2025, 8, 22, 14, 00);
        LocalDateTime expectedTo = LocalDateTime.of(2025, 8, 22, 16, 00);
        assertEquals(expectedFrom, e.getFrom());
        assertEquals(expectedTo, e.getTo());
    }

    @Test
    void assignTask_invalidFirstLetter_throwsStorageException() {
        String line = "X | 0 | Something unknown";
        assertThrows(StorageException.class, () -> storage.assignTask(line));
    }

    @Test
    void assignTask_invalidFormat_throwsStorageException() {
        String line = "T | 0 | 1 | read book ";
        assertThrows(StorageException.class, () -> storage.assignTask(line));
    }

    @Test
    void assignTask_invalidIsDone_throwsStorageException() {
        String line = "T | 2 | read book";
        assertThrows(StorageException.class, () -> storage.assignTask(line));
    }

    @Test
    void assignTask_invalidDeadline_throwsStorageException() {
        String line = "D | 0 | code";
        assertThrows(StorageException.class, () -> storage.assignTask(line));
    }

    @Test
    void assignTask_invalidEvent_throwsStorageException() {
        String line = "E | 1 | lecture | 22 August 2025 2:00pm";
        assertThrows(StorageException.class, () -> storage.assignTask(line));
    }

    @Test
    void assignTask_invalidDateFormat_throwsStorageException() {
        String line = "D | 0 | read book | 22/8/2025 2:00pm";
        assertThrows(StorageException.class, () -> storage.assignTask(line));
    }
}
