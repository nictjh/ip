package task;

public class EventTask extends Task {

    private final String from;
    private final String to;

    public EventTask(String desc, String from, String to) {
        super(desc);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    @Override
    public String toString() {
        String eventID = "[E]";
        return eventID + super.toString() + "(from:" + this.from + "to:" + this.to + ")";
    }
}
