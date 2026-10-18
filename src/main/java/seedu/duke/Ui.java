package seedu.duke;

import java.util.Scanner;

/**
 * Handles reading user input and displaying messages to the console.
 */
public class Ui {
    /** ASCII art banner displayed on startup. */
    private static final String BANNER = "    ____  __                __        __  _                   ___ \n"
            + "   / __ \\/ /___ ___  ______/ /_____ _/ /_(_)___  ____   _   <  / \n"
            + "  / /_/ / / __ `/ / / / __/ __/ __ `/ __/ / __ \\/ __ \\ |_|  / /  \n"
            + " / ____/ / /_/ / /_/ /\\__ \\ / /_/ / /_/ / /_/ / / / /      / /   \n"
            + "/_/   /_/\\__,_/\\__, /___/\\__\\__,_/\\__/_/\\____/_/ /_/      /_/    \n"
            + "              /____/\n";

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
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Input stream ended before an exit command was received");
        }
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
        System.out.println(BANNER);
    }

    /**
     * Prints the list of available commands.
     */
    public void showHelp() {
        System.out.println("Available commands:");
        System.out.println("  help  - Show this help message");
        System.out.println("  list  - Show all games and their IDs");
        System.out.println("  play <gameId>  - Start a game by its ID");
        System.out.println("  quit  - Leave the current game and return to this menu");
        System.out.println("  exit  - Exit the application");
    }

    /**
     * Prints the goodbye message.
     */
    public void showGoodbye() {
        System.out.println("Bye!");
    }

    /**
     * Prints the game startup message
     *
     * @param gameName the title of the loading game
     */
    public void showGameStarting(String gameName) {
        System.out.println("Starting " + gameName + "...");
    }

    /**
     * Prints all available games
     *
     * @param gameList the list of games
     */
    public void showListOfGames(GameList gameList) {
        assert !gameList.getGames().isEmpty() : "There should always be at least one game";
        System.out.println("Available games:");

        for (Game game : gameList.getGames()) {
            System.out.println("  " + game.getGameName() + " | ID: " + game.getGameId());
        }
    }
}
