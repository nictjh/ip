package task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class DeadlineTask extends Task{


    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy h:mm a");
    private static final DateTimeFormatter[] INPUT_FORMATS = new DateTimeFormatter[] {
        DateTimeFormatter.ofPattern("d/M/yyyy HHmm").withResolverStyle(ResolverStyle.LENIENT),
        DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm").withResolverStyle(ResolverStyle.LENIENT),
        DateTimeFormatter.ofPattern("yyyy-M-d HHmm").withResolverStyle(ResolverStyle.LENIENT),
        DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm").withResolverStyle(ResolverStyle.LENIENT),
    };
    private static final DateTimeFormatter[] WRITE_FORMATS = new DateTimeFormatter[] {
        DateTimeFormatter.ofPattern("dd/M/yyyy HHmm"),
    };
    private final String deadline;
    protected LocalDateTime by;

    public DeadlineTask(String desc, String deadline) {
        super(desc);
        this.deadline = deadline.trim();
        try {
            this.by = parseDateTime(deadline);
        } catch (DateTimeParseException e) {
            this.by = null;
        }
    }

    private static LocalDateTime parseDateTime(String input) {
        for (DateTimeFormatter format : INPUT_FORMATS) {
            try {
                return LocalDateTime.parse(input, format);
            } catch (DateTimeParseException e) {
                // Do nothing
            }
        }

        System.out.println("❌ Failed to parse: " + input);
        return null;
    }

    private static String formatOutputDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }

        return dateTime.format(OUTPUT_FORMAT);
    }

    public LocalDate getLocalDate() {
        return (this.by != null) ? this.by.toLocalDate() : null;
    }

    public LocalDateTime getDateTime() {
        // Getter function
        return this.by;
    }

    public String getDeadline() {
        // Getter function
        return this.deadline;
    }

    // Need to account this for in the save Command too
    public String writeDateTime() {
        if (this.by == null) {
            return "";
        }
        for (DateTimeFormatter format : WRITE_FORMATS) {
            try {
                return this.by.format(format);
            } catch (DateTimeParseException e) {
                // Do nothing
            }
        }
        return "";
    }

    @Override
    public String toString() {
        String deadLineID = "[D]";
        String formattedDateTime = (this.by != null) ? formatOutputDateTime(this.by) : "";
        return deadLineID + super.toString() + "(by: " + ((!formattedDateTime.isEmpty()) ? formattedDateTime : this.deadline)  + ")";
    }

}
