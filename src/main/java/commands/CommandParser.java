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
        assert entryArray != null : "entryArray should not be null";
        assert entryArray.length > 1 : "entryArray should have at least one argument after the command";
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
        assert requiredNum > 0 : "requiredNum should be greater than 0";
        assert args != null : "args should not be null";
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
        assert input != null : "input should not be null";
        String[] inputArray = input.split(" ");
        assert inputArray.length > 0 : "inputArray should have at least one element";
        String command = parseCommandType(inputArray); // Getting the commandType
        assert command != null : "command should not be null";

        return createCommand(command, input, inputArray);
    }
    /**
     * Extract command type from the input array
     *
     * @param inputArray The array of strings from the user input
     * @return The command type as a String
     */
    private static String parseCommandType(String[] inputArray) {
        return inputArray[0];
    }

    /**
     * Creates and return corresponding Command object based on the command type
     *
     * @param command The command type
     * @param input The full user input
     * @param inputArray The array of strings from the user input
     * @return The corresponding Command object
     * @throws MissingArgumentException If the user input is missing arguments
     */
    private static Command createCommand(String command, String input, String[] inputArray)
            throws MissingArgumentException {
        switch (command) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "mark":
            return handleMarkCommand(inputArray);
        case "unmark":
            return handleUnmarkCommand(inputArray);
        case "todo":
            return handleTodoCommand(inputArray);
        case "deadline":
            return handleDeadlineCommand(inputArray, input);
        case "event":
            return handleEventCommand(inputArray, input);
        case "delete":
            return handleDeleteCommand(inputArray);
        case "find":
            return handleFindCommand(inputArray);
        case "findDate":
            return handleFindDateCommand(inputArray);
        case "save":
            return new SaveCommand(FILE_PATH);
        default:
            return new UnknownCommand(); // throws error need to be caught in app.Solace
        }
    }
    /**
     * Handles the "mark" command.
     */
    private static Command handleMarkCommand(String[] inputArray) throws MissingArgumentException {
        validateArguments(inputArray, 2);
        int[] indexes = new int[inputArray.length - 1];
        for (int i = 1; i < inputArray.length; i++) {
            indexes[i - 1] = Integer.parseInt(inputArray[i]);
        }
        return new MarkCommand(indexes);
    }
    /**
     * Handles the "unmark" command.
     */
    private static Command handleUnmarkCommand(String[] inputArray) throws MissingArgumentException {
        validateArguments(inputArray, 2);
        return new UnmarkCommand(Integer.parseInt(inputArray[1]));
    }
    /**
     * Handles the "todo" command.
     */
    private static Command handleTodoCommand(String[] inputArray) throws MissingArgumentException {
        if (inputArray.length < 2) {
            throw new MissingToDoDescException();
        }
        String toDoDesc = extractDescription(inputArray);
        return new CreateToDoCommand(toDoDesc);
    }
    /**
     * Handles the "deadline" command.
     */
    private static Command handleDeadlineCommand(String[] inputArray, String input)
            throws MissingDeadlineDetailsException {
        if (inputArray.length < 2 || !input.contains("/by")) {
            throw new MissingDeadlineDetailsException();
        }
        String deadlineDesc = extractDescription(inputArray);
        String dDesc = deadlineDesc.substring(0, deadlineDesc.indexOf("/by"));
        String by = deadlineDesc.substring(deadlineDesc.indexOf("/by") + 4);
        return new CreateDeadlineCommand(dDesc, by);
    }
    /**
     * Handles the "event" command.
     */
    private static Command handleEventCommand(String[] inputArray, String input) throws MissingEventDetailsException {
        if (inputArray.length < 2 || !input.contains("/from") || !input.contains("/to")) {
            throw new MissingEventDetailsException();
        }
        String eventDesc = extractDescription(inputArray);
        String eDesc = eventDesc.substring(0, eventDesc.indexOf("/from"));
        String from = eventDesc.substring(eventDesc.indexOf("/from") + 6, eventDesc.indexOf("/to"));
        String to = eventDesc.substring(eventDesc.indexOf("/to") + 4);
        return new CreateEventCommand(eDesc, from, to);
    }
    /**
     * Handles the "delete" command.
     */
    private static Command handleDeleteCommand(String[] inputArray) throws MissingArgumentException {
        validateArguments(inputArray, 2);
        int deleteIndex = Integer.parseInt(inputArray[1]);
        return new DeleteTaskCommand(deleteIndex);
    }
    /**
     * Handles the "findDate" command.
     */
    private static Command handleFindDateCommand(String[] inputArray) throws MissingArgumentException {
        validateArguments(inputArray, 2);
        String findString = inputArray[1];
        return new FindDateCommand(findString);
    }
    /**
     * Handles the "find" command.
     */
    private static Command handleFindCommand(String[] inputArray) {
        String desc = extractDescription(inputArray);
        return new FindCommand(desc);
    }
}
