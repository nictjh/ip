package commands;
import app.Solace;
import exceptions.MissingArgumentException;
import exceptions.MissingDeadlineDetailsException;
import exceptions.MissingEventDetailsException;
import exceptions.MissingToDoDescException;

public class CommandParser {

    private static final String FILE_PATH = "bin/storage";

    private static String extractDescription(String[] entryArray) {
        StringBuilder desc = new StringBuilder();
        for (int i = 1; i < entryArray.length; ++i) {
            desc.append(entryArray[i]).append(" ");
        }
        return desc.toString().trim();
    }

    private static void validateArguments(String[] args, int requiredNum) throws MissingArgumentException {
        if (args.length < requiredNum) {
            throw new MissingArgumentException("Missing arguments in command, please try again with a number behind.");
        }
    }

    // returns Command to be executed in Solace
    public static Command parse(String input) throws MissingArgumentException {
        String[] inputArray = input.split(" ");
        String command = inputArray[0]; // Getting the commandType

        switch (command) {
            case "bye":
                return new ExitCommand();
            case "list":
                return new ListCommand();
            case "mark":
                validateArguments(inputArray, 2);
                return new MarkCommand(Integer.parseInt(inputArray[1]));
            case "unmark":
                validateArguments(inputArray, 2);
                return new UnmarkCommand(Integer.parseInt(inputArray[1]));
            case "todo":
                if (inputArray.length < 2) {
                    throw new MissingToDoDescException();
                }
                String toDoDesc = extractDescription(inputArray);
                return new CreateToDoCommand(toDoDesc);
            case "deadline":
                if (inputArray.length < 2 || !input.contains("/by")) {
                    throw new MissingDeadlineDetailsException();
                }
                String deadlineDesc = extractDescription(inputArray);
                String dDesc = deadlineDesc.substring(0, deadlineDesc.indexOf("/by"));
                String by = deadlineDesc.substring(deadlineDesc.indexOf("/by") + 4); // Increment one more than before to remove leading space
                return new CreateDeadlineCommand(dDesc, by);
            case "event":
                if (inputArray.length < 2 || !input.contains("/from") || !input.contains("/to")) {
                    throw new MissingEventDetailsException();
                }
                String eventDesc = extractDescription(inputArray);
                String eDesc = eventDesc.substring(0, eventDesc.indexOf("/from"));
                String from = eventDesc.substring(eventDesc.indexOf("/from") + 6, eventDesc.indexOf("/to")); // All values here incremented by 1
                String to = eventDesc.substring(eventDesc.indexOf("/to") + 4); // Incremented by 1
                return new CreateEventCommand(eDesc, from, to);
            case "delete":
                validateArguments(inputArray, 2);
                int deleteIndex = Integer.parseInt(inputArray[1]);
                return new DeleteTaskCommand(deleteIndex);
            case "find":
                validateArguments(inputArray, 2);
                String findString = inputArray[1];
                return new FindDateCommand(findString);
            case "save":
                return new SaveCommand(FILE_PATH);
            default:
                return new UnknownCommand(); // throws error need to be caught in Solace
        }
    }

}
