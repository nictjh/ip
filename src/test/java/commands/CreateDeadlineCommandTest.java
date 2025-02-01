package commands;
import exceptions.InvalidTaskNumberException;
import exceptions.RepeatedTaskUpdateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import commands.MarkCommand;
import task.DeadlineTask;
import taskList.TaskList;
import task.ToDoTask;
import static org.junit.jupiter.api.Assertions.*;

public class CreateDeadlineCommandTest {

    private TaskList tasklist;

    @BeforeEach
    public void setUp() {
        this.tasklist = new TaskList();
    }

    @Test
    void testCreateDeadlineCommand() {
        CreateDeadlineCommand createDeadlineCommand = new CreateDeadlineCommand("read book ", "01/02/2025 1800");
        String statusMsg = createDeadlineCommand.execute(this.tasklist);

        assertEquals(1, this.tasklist.getList().size()); // Verify the new size of tasklist
        assertEquals("Got it. I've added this task:\n\t[D][ ] read book (by: Feb 1 2025 6:00 pm)\n",
                statusMsg);
        assertInstanceOf(DeadlineTask.class, this.tasklist.getTask(0),
                "Task 1 should be a Deadline Task");
    }

    @Test
    void testCreateMultipleDeadlines() {
        new CreateDeadlineCommand("read book", "01/02/2025 1800").execute(this.tasklist);
        new CreateDeadlineCommand("gym", "02/02/2025 1000").execute(this.tasklist);

        // Verify size after multiple additions
        assertEquals(2, this.tasklist.getList().size(), "Task list size should be 2 after adding two deadlines.");
    }

    @Test
    void testDeadlineDateFormatting() {
        CreateDeadlineCommand createDeadlineCommand = new CreateDeadlineCommand("Submit report", "05/02/2025 1730");
        String statusMsg = createDeadlineCommand.execute(this.tasklist);

        // Verify the formatting is correct
        assertTrue(statusMsg.contains("Feb 5 2025 5:30 pm"), "Deadline date should be formatted as 'MMM d yyyy h:mm a'.");
    }

}
