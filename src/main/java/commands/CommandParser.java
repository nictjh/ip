package commands;
import exceptions.MissingArgumentException;
import exceptions.MissingDeadlineDetailsException;
import exceptions.MissingEventDetailsException;
import exceptions.MissingToDoDescException;

/**
 * Parses user input and returns the corresponding Command object
 *
 */
public class CommandParser {

    private static final String FILE_PATH = "bin/storage";

    /**
     * Extracts the description from the input array
     *
     * @param entryArray The array of strings from the user input
     * @return The full description of the task including the deadlines or event timings
     */
    private static String extractDescription(String[] entryArray) {
        StringBuilder desc = new StringBuilder();
        for (int i = 1; i < entryArray.length; ++i) {
            desc.append(entryArray[i]).append(" ");
        }
        return desc.toString().trim();
    }

    /**
     * Validates the number of arguments in the command
     *
     * @param args The array of strings from the user input
     * @param requiredNum The number of arguments required for the command
     * @throws MissingArgumentException If the number of arguments is less than the required number
     */
    private static void validateArguments(String[] args, int requiredNum) throws MissingArgumentException {
        if (args.length < requiredNum) {
            throw new MissingArgumentException("Missing arguments in command, please try again with a number behind.");
        }
    }

    /**
     * Parses the user input and returns the corresponding Command object
     *
     * @param input The user input
     * @return Command object corresponding to the user input
     * @throws MissingArgumentException If the user input is missing arguments
     */
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
            // After validating I should remove the "mark" from the inputArray
            // return new MarkCommand(Integer.parseInt(inputArray[1]));
            int[] indexes = new int[inputArray.length - 1];
            for (int i = 1; i < inputArray.length; i++) {
                indexes[i - 1] = Integer.parseInt(inputArray[i]);
            }
            return new MarkCommand(indexes);
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
            String by = deadlineDesc.substring(deadlineDesc.indexOf("/by") + 4);
            return new CreateDeadlineCommand(dDesc, by);
        case "event":
            if (inputArray.length < 2 || !input.contains("/from") || !input.contains("/to")) {
                throw new MissingEventDetailsException();
            }
            String eventDesc = extractDescription(inputArray);
            String eDesc = eventDesc.substring(0, eventDesc.indexOf("/from"));
            String from = eventDesc.substring(eventDesc.indexOf("/from") + 6, eventDesc.indexOf("/to"));
            String to = eventDesc.substring(eventDesc.indexOf("/to") + 4);
            return new CreateEventCommand(eDesc, from, to);
        case "delete":
            validateArguments(inputArray, 2);
            int deleteIndex = Integer.parseInt(inputArray[1]);
            return new DeleteTaskCommand(deleteIndex);
        case "findDate":
            validateArguments(inputArray, 2);
            String findString = inputArray[1];
            return new FindDateCommand(findString);
        case "find":
            String desc = extractDescription(inputArray);
            return new FindCommand(desc);
        case "save":
            return new SaveCommand(FILE_PATH);
        default:
            return new UnknownCommand(); // throws error need to be caught in app.Solace
        }
    }
}
