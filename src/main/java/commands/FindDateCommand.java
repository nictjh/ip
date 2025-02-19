package commands;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import app.Solace;
import ui.Ui;

/**
 * Represents the command to find tasks by date
 *
 */
public class FindDateCommand extends Command {

    private final LocalDate DATE;
    private static final DateTimeFormatter[] INPUT_FORMATS = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("d/M/yyyy").withResolverStyle(ResolverStyle.LENIENT),
            DateTimeFormatter.ofPattern("dd/MM/yyyy").withResolverStyle(ResolverStyle.LENIENT),
            DateTimeFormatter.ofPattern("yyyy-M-d").withResolverStyle(ResolverStyle.LENIENT),
            DateTimeFormatter.ofPattern("yyyy-MM-dd").withResolverStyle(ResolverStyle.LENIENT),
    };

    /**
     * Creates a new FindDateCommand object
     *
     * @param date The date to find tasks on
     */
    public FindDateCommand(String date) {
        this.DATE = parseDateTime(date);
    }

    @Override
    public String execute(app.Solace solace) {
        logExecution();
        try {
            displayStatusMessage(solace, solace.getTaskList().findTasksByDate(this.DATE));
            return solace.getTaskList().findTasksByDate(this.DATE);
        } catch (NullPointerException e) {
            displayStatusMessage(solace, "No tasks found on " + this.DATE);
            return "No tasks found on " + this.DATE;
        }
    }

    /**
     * Displays the status message of the command execution
     *
     * @param solace The Solace instance to get the UI instance
     * @param message The status message to display
     */
    private void displayStatusMessage(Solace solace, String message) {
        Ui ui = solace.getUi();
        ui.printMessage(message);
    }
    @Override
    public void logExecution() {
        System.out.println("Find Date Command is executed");
    }

    /**
     * Parses the input date string into a LocalDate object
     * accepts multiple date formats
     *
     * @param input The input date string
     * @return The parsed LocalDate object or null if parsing fails
     */
    private static LocalDate parseDateTime(String input) {
        for (DateTimeFormatter format : INPUT_FORMATS) {
            try {
                return LocalDate.parse(input, format);
            } catch (DateTimeParseException e) {
                System.out.println("Date format not matched in FindDate Command!");
            }
        }

        return null;
    }
}
