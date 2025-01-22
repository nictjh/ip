package task;

public class ToDoTask extends Task {
    //Tasks without any date/time attached to it

    public ToDoTask(String desc) {
        super(desc);
    }

    @Override
    public String toString() {
        String toDoId = "[T]";
        return toDoId + super.toString();
    }
}
