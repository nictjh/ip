package task;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class EventTask extends Task {

    private final String from;
    private final String to;
    protected LocalDateTime start;
    protected LocalDateTime end;
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy h:mm a");
    private static final DateTimeFormatter[] INPUT_FORMATS = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm").withResolverStyle(ResolverStyle.LENIENT),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm").withResolverStyle(ResolverStyle.LENIENT),
            DateTimeFormatter.ofPattern("yyyy-M-d HHmm").withResolverStyle(ResolverStyle.LENIENT),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm").withResolverStyle(ResolverStyle.LENIENT)
    };

    private static final DateTimeFormatter[] WRITE_FORMATS = new DateTimeFormatter[] {
            DateTimeFormatter.ofPattern("dd/M/yyyy HHmm"),
    };

    public EventTask(String desc, String from, String to) {
        super(desc);
        this.from = from.trim();
        this.to = to.trim();
        try {
            this.start = parseDateTime(from.replaceAll("\\s+$", "")); // trims the trailing backspaces
        } catch (DateTimeParseException e) {
            this.start = null;
        }
        try {
            this.end = parseDateTime(to);
        } catch (DateTimeParseException e) {
            this.end = null;
        }
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    public LocalDateTime getStartDateTime() {
        // Getter function
        return this.start;
    }

    public LocalDateTime getEndDateTime() {
        // Getter function
        return this.end;
    }

    public LocalDate getStartLocalDate() {
        return (this.start != null) ? this.start.toLocalDate() : null;
    }

    public LocalDate getEndLocalDate() {
        return (this.end != null) ? this.end.toLocalDate() : null;
    }

    private static LocalDateTime parseDateTime(String input) {
        for (DateTimeFormatter format : INPUT_FORMATS) {
            try {
                return LocalDateTime.parse(input, format);
            } catch (DateTimeParseException e) {
                // Do nothing
            }
        }
        return null;
    }

    private static String formatOutputDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(OUTPUT_FORMAT);
    }

    public String writeDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        for (DateTimeFormatter format : WRITE_FORMATS) {
            try {
                return dateTime.format(format);
            } catch (DateTimeParseException e) {
                // Do nothing
            }
        }
        return "";
    }

    @Override
    public String toString() {
        String eventID = "[E]";
        String formattedStart = (this.start != null) ? formatOutputDateTime(this.start) : this.from;
        String formattedEnd = (this.end != null) ? formatOutputDateTime(this.end) : this.to;
        return eventID + super.toString() + "(from: " + ((!formattedStart.isEmpty()) ? formattedStart : this.from)
                + " to: " + ((!formattedEnd.isEmpty()) ? formattedEnd : this.to ) + ")";
    }
}
