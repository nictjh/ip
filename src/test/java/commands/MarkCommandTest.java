package commands;

import exceptions.InvalidTaskNumberException;
import exceptions.RepeatedTaskUpdateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasklist.TaskList;
import task.ToDoTask;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test class for the MarkCommand
 */
public class MarkCommandTest {

    private TaskList tasklist;

    @BeforeEach
    public void setUp() {
        this.tasklist = new TaskList();
        this.tasklist.addTask(new ToDoTask("read book"));
        this.tasklist.addTask(new ToDoTask("gym"));
    }

    @Test
    void markCommand_validTask_marksTaskAsDone() throws RepeatedTaskUpdateException, InvalidTaskNumberException {
        //Verify of task 1 @start
        assertFalse(this.tasklist.getTask(0).showStatus(), "Task 1 should not be marked done initially");
        MarkCommand markCommand = new MarkCommand(1); // marks the first task
        markCommand.execute(this.tasklist);

        //Verify status of task 1
        assertTrue(this.tasklist.getTask(0).showStatus(), "Task 1 should be marked done");
    }

    @Test
    void markCommand_multipleTasks_markTasksAsDone() throws RepeatedTaskUpdateException, InvalidTaskNumberException {

        assertFalse(this.tasklist.getTask(0).showStatus(), "Task 1 should not be marked done initially");
        assertFalse(this.tasklist.getTask(1).showStatus(), "Task 2 should not be marked done initially");

        MarkCommand markCommand = new MarkCommand(1, 2); // Mark first and second tasks
        markCommand.execute(this.tasklist);

        assertTrue(this.tasklist.getTask(0).showStatus(), "Task 1 should be marked done");
        assertTrue(this.tasklist.getTask(1).showStatus(), "Task 2 should be marked done");
    }

    @Test
    void markCommand_invalidIndex_throwsInvalidTaskNumberException() {
        // Test for negative index
        Exception negativeIndexException = assertThrows(InvalidTaskNumberException.class, () -> {
            new MarkCommand(0).execute(this.tasklist);
        });

        // Test for out of bounds index
        Exception outOfBoundsException = assertThrows(InvalidTaskNumberException.class, () -> {
            new MarkCommand(3).execute(this.tasklist); // Index > size
        });

        // Verify the exception messages
        assertEquals("Error! Invalid Task number entered.\nDo /list to check which tasks are available.",
                negativeIndexException.getMessage());
        assertEquals("Error! Invalid Task number entered.\nDo /list to check which tasks are available.",
                outOfBoundsException.getMessage());
    }

    @Test
    void markCommand_alreadyMarkedTask_throwsRepeatedTaskUpdateException() {
        // Mark the first task
        assertDoesNotThrow(() -> new MarkCommand(2).execute(this.tasklist));

        // Mark the first task again
        Exception exception = assertThrows(RepeatedTaskUpdateException.class, () -> {
            new MarkCommand(2).execute(this.tasklist);
        });

        // Verify the exception message
        assertEquals("Error! This task has already been updated before.", exception.getMessage());
    }
}
