package app;
import commands.ExitCommand;

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

    public Solace() {
        System.out.println(logo);
        System.out.println("Hello! I'm Solace\nWhat can I do for you?\n" + this.getDividerLine());
    }

    public void setAlive(boolean alive) {
        // Setter function
        isAlive = alive;
    }

    public String getDividerLine() {
        // Getter function
        return dividerLine;
    }


    public static void main(String[] args) {
        Solace solace = new Solace();
        Scanner input = new Scanner(System.in);

        while (isAlive) {
            String command = input.nextLine(); // Gets the command
            System.out.println(dividerLine);
            System.out.println(command + "\n" + dividerLine);

            if (command.equals("bye")) {
                ExitCommand exitCommand = new ExitCommand();
                exitCommand.execute(solace);
            }
        }
    }
}
