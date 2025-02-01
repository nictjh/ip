package ui;

import java.util.Scanner;

// This Ui Class will handle all the input/output of the program
public class Ui {
    private static final String dividerLine = "--------------------------------------";
    private static final String logo =
            "  _____       _\n"
            + " / ____|     | |\n"
            + "| (___   ___ | | __ _  ___ ___\n"
            + " \\___ \\ / _ \\| |/ _` |/ __/ _ \\\n"
            + " ____) | (_) | | (_| | (_|  __/\n"
            + "|_____/ \\___/|_|\\__,_|\\___\\___|\n";

    public Ui() {
        System.out.println(logo);
        System.out.println("Hello! I'm Solace\nWhat can I do for you?\n" + this.getDividerLine());
    }

    public String getDividerLine() {
        return dividerLine;
    }

    public String readCommand(Scanner sc) {
        return sc.nextLine();
    }

    public void printMessage(String message) {
        System.out.println(message + "\n" + this.getDividerLine());
    }

}
