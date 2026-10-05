package seedu.duke;

import java.util.Scanner;

/**
 * Handles reading user input and displaying messages to the console.
 */
public class Ui {
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Reads a line of input from the user.
     *
     * @return The trimmed input string.
     */
    public String readLine() {
        return scanner.nextLine().trim();
    }

    /**
     * Prints a message to the console.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints the welcome banner.
     */
    public void showBanner() {
        String banner = "    ____  __                __        __  _                   ___ \n"
                + "   / __ \\/ /___ ___  ______/ /_____ _/ /_(_)___  ____   _   <  / \n"
                + "  / /_/ / / __ `/ / / / __/ __/ __ `/ __/ / __ \\/ __ \\ |_|  / /  \n"
                + " / ____/ / /_/ / /_/ /\\__ \\ / /_/ / /_/ / /_/ / / / /      / /   \n"
                + "/_/   /_/\\__,_/\\__, /___/\\__\\__,_/\\__/_/\\____/_/ /_/      /_/    \n"
                + "              /____/\n";
        System.out.println(banner);
    }

    /**
     * Prints the list of available commands.
     */
    public void showHelp() {
        System.out.println("Available commands:");
        System.out.println("  help  - Show this help message");
        System.out.println("  exit  - Exit the application");
    }

    /**
     * Prints the goodbye message.
     */
    public void showGoodbye() {
        System.out.println("Bye!");
    }
}