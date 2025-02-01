package app;
import commands.ExitCommand;
import commands.ListCommand;
import commands.SaveCommand;
import taskList.TaskList;
import task.Task;
import task.DeadlineTask;
import task.EventTask;
import task.ToDoTask;
import exceptions.EmptyTaskListException;
import exceptions.InvalidTaskNumberException;
import exceptions.MissingArgumentException;
import exceptions.UnknownCommand;
import exceptions.MissingDeadlineDetailsException;
import exceptions.MissingEventDetailsException;
import exceptions.MissingToDoDescException;
import exceptions.RepeatedTaskUpdateException;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class Solace {

    private static boolean isAlive = true;
    private static final String dividerLine = "--------------------------------------";
    private static final String logo =
            "  _____       _\n"
            + " / ____|     | |\n"
            + "| (___   ___ | | __ _  ___ ___\n"
            + " \\___ \\ / _ \\| |/ _` |/ __/ _ \\\n"
            + " ____) | (_) | | (_| | (_|  __/\n"
            + "|_____/ \\___/|_|\\__,_|\\___\\___|\n";
    private final TaskList taskList;
    private final String filePath = "bin/storage";

    public Solace() {
        System.out.println(logo);
        System.out.println("Hello! I'm Solace\nWhat can I do for you?\n" + this.getDividerLine());
        // this.taskList = new TaskList(); // Need to edit this part so as to load from file
        File file = new File(filePath + File.separator + "taskList.txt");
        if (file.exists()) {
            this.taskList = new TaskList(file);
        } else {
            this.taskList = new TaskList();
        }
    }

    public void setAlive(boolean alive) {
        // Setter function
        isAlive = alive;
    }

    public String getDividerLine() {
        // Getter function
        return dividerLine;
    }

    public TaskList getTaskList() {
        // Getter function
        return taskList;
    }

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


    public static void main(String[] args) {
        Solace solace = new Solace();
        Scanner input = new Scanner(System.in);

        while (isAlive) {

            try {

                if (!input.hasNextLine()) {
                    break; //Fixing bug when there is no line to read but below call nextLine
                }

                String entry = input.nextLine(); // Gets user input
                String[] entryArray = entry.split(" "); // future commands might have more details
                String command = entryArray[0]; // first word is the command
                System.out.println(dividerLine); // Line after every command

                switch (command) {
                    case "bye":
                        ExitCommand exitCommand = new ExitCommand();
                        exitCommand.execute(solace);
                        break;
                    case "mark":
                        validateArguments(entryArray, 2);
                        int markIndex = Integer.parseInt(entryArray[1]);
                        System.out.println(solace.taskList.markTask(markIndex)
                                + dividerLine);
                        break;
                    case "unmark":
                        validateArguments(entryArray, 2);
                        int unmarkIndex = Integer.parseInt(entryArray[1]);
                        System.out.println(solace.taskList.unmarkTask(unmarkIndex)
                                + dividerLine);
                        break;
                    case "list":
                        ListCommand listCommand = new ListCommand();
                        listCommand.execute(solace);
                        break;
                    case "todo":
                        if (entryArray.length < 2) {
                            throw new MissingToDoDescException();
                        }
                        String toDoDesc = extractDescription(entryArray);
                        System.out.println(solace.taskList.addTask(new ToDoTask(toDoDesc))
                                + solace.taskList.getSize()
                                + dividerLine);
                        break;
                    case "deadline":
                        if (entryArray.length < 2 || !entry.contains("/by")) {
                            throw new MissingDeadlineDetailsException();
                        }
                        String deadlineString = extractDescription(entryArray);
                        String dDesc = deadlineString.substring(0, deadlineString.indexOf("/by"));
                        String deadLine = deadlineString.substring(deadlineString.indexOf("/by") + 3);
                        System.out.println(solace.taskList.addTask(new DeadlineTask(dDesc, deadLine))
                                + solace.taskList.getSize()
                                + dividerLine);
                        break;
                    case "event":
                        if (entryArray.length < 2 || !entry.contains("/from") || !entry.contains("/to")) {
                            throw new MissingEventDetailsException();
                        }
                        String eventString = extractDescription(entryArray);
                        String eDesc = eventString.substring(0, eventString.indexOf("/from"));
                        String eFrom = eventString.substring(eventString.indexOf("/from") + 5, eventString.indexOf("/to"));
                        String eTo = eventString.substring(eventString.indexOf("/to") + 3);
                        System.out.println(solace.taskList.addTask(new EventTask(eDesc, eFrom, eTo))
                                + solace.taskList.getSize()
                                + dividerLine);
                        break;
                    case "delete":
                        validateArguments(entryArray, 2);
                        int deleteIndex = Integer.parseInt(entryArray[1]);
                        System.out.println(solace.taskList.removeTask(deleteIndex)
                                + solace.taskList.getSize()
                                + dividerLine);
                        break;
                    case "save":
                        SaveCommand saveCommand = new SaveCommand(solace.filePath);
                        saveCommand.execute(solace);
                        break;
                    default:
                        throw new UnknownCommand();
                }

            } catch (MissingArgumentException | UnknownCommand | InvalidTaskNumberException
                     | RepeatedTaskUpdateException | EmptyTaskListException e) {
                System.out.println(e.getMessage());
                System.out.println(dividerLine);
            }
        }
        input.close();
    }
}
