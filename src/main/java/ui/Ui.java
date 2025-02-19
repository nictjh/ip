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
    private static final String[] greetings = {
        "Hey there! 👋 Ready to chat?",
        "Hello! Solace at your service. How can I help today? 😊",
        "Yo! What's cookin'? Need some help? 🍳",
        "Hiya! What’s on your mind? 🤔",
        "Greetings, human! What wisdom do you seek? 🧙"
    };

    private static final String[] goodbyes = {
        "Goodbye! Hope to chat with you again soon! 👋",
        "See ya! Don't miss me too much! 😜",
        "Bye-bye! Take care and stay awesome! 🌟",
        "Farewell, traveler! May your journey be grand! 🧙‍♂️",
        "Adios! Until our next adventure! 🌈"
    };
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
        return logo + "\n" + dividerLine + "\n" + getRandomGreeting();
    }

    /**
     * Returns an exit message when the user quits.
     */
    public static String getGoodbyeMessage() {
        return getRandomGoodbye();
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

    /**
     * Randomly selects a greeting from the list.
     *
     * @return A random greeting message.
     */
    public static String getRandomGreeting() {
        int index = (int) (Math.random() * greetings.length);
        return greetings[index];
    }

    /**
     * Randomly selects a goodbye message from the list.
     *
     * @return A random goodbye message.
     */
    public static String getRandomGoodbye() {
        int index = (int) (Math.random() * goodbyes.length);
        return goodbyes[index];
    }
}
