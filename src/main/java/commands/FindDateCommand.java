package commands;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

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
        // System.out.println("FindDateCommand Parsed Date~~~~~~~~~~~~~~ : " + date);
        // System.out.println("FindDateCommand Parsed Date~~~~~~~~~~~~~~ : " + this.DATE);
    }

    @Override
    public String execute(app.Solace solace) {
        Ui ui = solace.getUi();
        try {
            ui.printMessage(solace.getTaskList().findTasksByDate(this.DATE));
            return solace.getTaskList().findTasksByDate(this.DATE);
        } catch (NullPointerException e) {
            ui.printMessage("No tasks found on " + this.DATE);
            return "No tasks found on " + this.DATE;
        }
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
                // Do nothing
            }
        }

        return null;
    }
}
