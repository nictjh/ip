package commands;
import ui.Ui;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.LocalDate;

public class FindDateCommand extends Command {

    private final LocalDate DATE;
    private static final DateTimeFormatter[] INPUT_FORMATS = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("d/M/yyyy").withResolverStyle(ResolverStyle.LENIENT),
            DateTimeFormatter.ofPattern("dd/MM/yyyy").withResolverStyle(ResolverStyle.LENIENT),
            DateTimeFormatter.ofPattern("yyyy-M-d").withResolverStyle(ResolverStyle.LENIENT),
            DateTimeFormatter.ofPattern("yyyy-MM-dd").withResolverStyle(ResolverStyle.LENIENT),
    };

    public FindDateCommand(String date) {
        this.DATE = parseDateTime(date);
//        System.out.println("FindDateCommand Parsed Date~~~~~~~~~~~~~~ : " + date);
//        System.out.println("FindDateCommand Parsed Date~~~~~~~~~~~~~~ : " + this.DATE);
    }

    @Override
    public void execute(app.Solace solace) {
        Ui ui = solace.getUi();
        try {
            ui.printMessage(solace.getTaskList().findTasksByDate(this.DATE));
        } catch (NullPointerException e) {
            ui.printMessage("No tasks found on " + this.DATE);
        }
    }

    @Override
    public void logExecution() {
        System.out.println("Find Date Command is executed");
    }

    private static LocalDate parseDateTime(String input) {
        for (DateTimeFormatter format : INPUT_FORMATS) {
            try {
                return LocalDate.parse(input, format);
            } catch (DateTimeParseException e) {
                // Do nothing
            }
        }

//        System.out.println("Failed to parse: " + input);
        return null;
    }
}
