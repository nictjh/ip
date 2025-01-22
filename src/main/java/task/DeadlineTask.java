package task;

public class DeadlineTask extends Task{

    private final String deadline;

    public DeadlineTask(String desc, String deadline) {
        super(desc);
        this.deadline = deadline;
    }

    public String getDeadline() {
        // Getter function
        return this.deadline;
    }

    @Override
    public String toString() {
        String deadLineID = "[D]";
        return deadLineID + super.toString() + "(by:" + this.deadline + ")";
    }

}
