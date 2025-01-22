package app;
import commands.*;
import taskList.TaskList;
import task.*;

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


    public Solace() {
        System.out.println(logo);
        System.out.println("Hello! I'm Solace\nWhat can I do for you?\n" + this.getDividerLine());
        this.taskList = new TaskList();
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


    public static void main(String[] args) {
        Solace solace = new Solace();
        Scanner input = new Scanner(System.in);

        while (isAlive) {

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
                    System.out.println(solace.taskList.markTask(Integer.parseInt(entryArray[1]))
                            + dividerLine);
                    break;
                case "unmark":
                    System.out.println(solace.taskList.unmarkTask(Integer.parseInt(entryArray[1]))
                            + dividerLine);
                    break;
                case "list":
                    ListCommand listCommand = new ListCommand();
                    listCommand.execute(solace);
                    break;
                case "todo":
                    StringBuilder toDoDesc = new StringBuilder();
                    for (int i = 1; i < entryArray.length; ++i) {
                        toDoDesc.append(entryArray[i]).append(" ");

                    }
                    System.out.println(solace.taskList.addTask(new ToDoTask(toDoDesc.toString().trim()))
                            + solace.taskList.getSize()
                            + dividerLine);
                    break;
                case "deadline":
                    StringBuilder deadlineDesc = new StringBuilder();
                    for (int i = 1; i < entryArray.length; ++i) {
                        deadlineDesc.append(entryArray[i]).append(" ");
                    }
                    String deadlineString = deadlineDesc.toString().trim();
                    String dDesc = deadlineString.substring(0, deadlineString.indexOf("/by"));
                    String deadLine = deadlineString.substring(deadlineString.indexOf("/by") + 3);
                    System.out.println(solace.taskList.addTask(new DeadlineTask(dDesc, deadLine))
                            + solace.taskList.getSize()
                            + dividerLine);
                    break;
                case "event":
                    StringBuilder eventDesc = new StringBuilder();
                    for (int i = 1; i < entryArray.length; ++i) {
                        eventDesc.append(entryArray[i]).append(" ");
                    }
                    String eventString = eventDesc.toString().trim();
                    String eDesc = eventString.substring(0, eventString.indexOf("/from"));
                    String eFrom = eventString.substring(eventString.indexOf("/from") + 5, eventString.indexOf("/to"));
                    String eTo = eventString.substring(eventString.indexOf("/to") + 3);
                    System.out.println(solace.taskList.addTask(new EventTask(eDesc, eFrom, eTo))
                            + solace.taskList.getSize()
                            + dividerLine);
                    break;
                default:
                    Task task = new Task(entry);
                    solace.taskList.addTask(task); // dont print out the message
                    System.out.println("\tadded: " + entry + "\n" + dividerLine); // Echo ability
            }
        }
        input.close();
    }
}
