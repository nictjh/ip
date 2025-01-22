package app;
import commands.ExitCommand;
import commands.ListCommand;
import taskList.TaskList;
import task.Task;

import java.util.Scanner;

public class Solace {

    private static boolean isAlive = true;
    private static final String dividerLine = "--------------------------------------";
    private static final String logo =
            "  _____       _                 \n"
            + " / ____|     | |                \n"
            + "| (___   ___ | | __ _  ___ ___  \n"
            + " \\___ \\ / _ \\| |/ _` |/ __/ _ \\ \n"
            + " ____) | (_) | | (_| | (_|  __/ \n"
            + "|_____/ \\___/|_|\\__,_|\\___\\___| \n";
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
                    System.out.println(solace.taskList.markTask(Integer.parseInt(entryArray[1])) + dividerLine);
                    break;
                case "unmark":
                    System.out.println(solace.taskList.unmarkTask(Integer.parseInt(entryArray[1])) + dividerLine);
                    break;
                case "list":
                    ListCommand listCommand = new ListCommand();
                    listCommand.execute(solace);
                    break;
                default:
                    Task task = new Task(entry);
                    solace.taskList.addTask(task);
                    System.out.println("\tadded: " + entry + "\n" + dividerLine); // Echo ability
            }
        }
    }
}
