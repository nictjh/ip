package app;
import commands.ExitCommand;
import commands.ListCommand;
import taskList.TaskList;
import task.Task;

import java.util.Scanner;
import java.util.ArrayList;

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
            String command = input.nextLine(); // Gets the command
            System.out.println(dividerLine);

            switch (command) {
                case "bye":
                    ExitCommand exitCommand = new ExitCommand();
                    exitCommand.execute(solace);
                    break;
                case "list":
                    ListCommand listCommand = new ListCommand();
                    listCommand.execute(solace);
                    continue;
                default:
                    Task task = new Task(command);
                    solace.taskList.addTask(task);
                    System.out.println(" added: " + command + "\n" + dividerLine); // Echo ability
            }
        }
    }
}
