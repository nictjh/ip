package ui;
import java.util.Scanner;

/**
 * Represents the User Interface of the application
 * Handles all input and output of the application
 */
public class Ui {

    private static final String dividerLine = "--------------------------------------";
    private static final String logo =
            "  _____       _\n"
            + " / ____|     | |\n"
            + "| (___   ___ | | __ _  ___ ___\n"
            + " \\___ \\ / _ \\| |/ _` |/ __/ _ \\\n"
            + " ____) | (_) | | (_| | (_|  __/\n"
            + "|_____/ \\___/|_|\\__,_|\\___\\___|\n";

    /**
     * Creates a new User Interface
     */
    public Ui() {
        System.out.println(logo);
        System.out.println("Hello! I'm Solace\nWhat can I do for you?\n" + this.getDividerLine());
    }

    public String getDividerLine() {
        return dividerLine;
    }

    /**
     * Returns the chatbot's welcome message.
     */
    public static String getWelcomeMessage() {
        return logo + "\n" + dividerLine + "\nHello! I'm Solace\nWhat can I do for you?\n";
    }

    /**
     * Returns an exit message when the user quits.
     */
    public static String getGoodbyeMessage() {
        return "Goodbye! Hope to see you again soon. 👋";
    }

    /**
     * Reads the next command from the user
     *
     * @param sc Scanner object to read input
     * @return The next command from the user in a String format
     */
    public String readCommand(Scanner sc) {
        return sc.nextLine();
    }

    /**
     * Prints the message to the user display
     *
     * @param message The message to be printed
     */
    public void printMessage(String message) {
        System.out.println(message + "\n" + this.getDividerLine());
    }

}
