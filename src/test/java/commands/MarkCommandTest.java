package commands;
import exceptions.InvalidTaskNumberException;
import exceptions.RepeatedTaskUpdateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import commands.MarkCommand;
import taskList.TaskList;
import task.ToDoTask;
import static org.junit.jupiter.api.Assertions.*;

public class MarkCommandTest {

    private TaskList tasklist;

    @BeforeEach
    public void setUp() {
        this.tasklist = new TaskList();
        this.tasklist.addTask(new ToDoTask("read book"));
        this.tasklist.addTask(new ToDoTask("gym"));
    }

    @Test
    void testMarkDoneCommand() throws RepeatedTaskUpdateException, InvalidTaskNumberException {
        //Verify of task 1 @start
        assertFalse(this.tasklist.getTask(0).getStatus(), "Task 1 should not be marked done initially");

        MarkCommand markCommand = new MarkCommand(1); // marks the first task
        markCommand.execute(this.tasklist);

        //Verify status of task 1
        assertTrue(this.tasklist.getTask(0).getStatus(), "Task 1 should be marked done");
    }

    @Test
    void testMarkInvalidIndex() {
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
    void testMarkAlreadyDoneTask() {
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
