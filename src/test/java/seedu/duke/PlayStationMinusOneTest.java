package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** End-to-end tests that drive the main command loop with scripted input. */
class PlayStationMinusOneTest {
    /** The real keyboard and console, saved so they can be put back after each test. */
    private InputStream originalIn;
    private PrintStream originalOut;

    @BeforeEach
    void saveConsole() {
        originalIn = System.in;
        originalOut = System.out;
    }

    @AfterEach
    void restoreConsole() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    /**
     * Runs the whole application as if the user typed the given lines, one per line.
     *
     * <p>System.in must be replaced before main is called, because main creates a
     * Ui whose Scanner reads from whatever System.in is at that moment. Log messages
     * go to System.err, so only the user-facing output is returned.
     *
     * @param lines the lines the simulated user types
     * @return everything the application printed to System.out
     */
    private String run(String... lines) {
        String typed = String.join("\n", lines) + "\n";
        System.setIn(new ByteArrayInputStream(typed.getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        PlayStationMinusOne.main(new String[0]);

        return output.toString();
    }

    @Test
    void listShowsAllGames() {
        String output = run("list", "exit");

        assertTrue(output.contains("[ID: 1] Wordle"));
        assertTrue(output.contains("[ID: 2] Calculator"));
        assertTrue(output.contains("[ID: 3] Battleship"));
        assertTrue(output.contains("[ID: 4] Tic Tac Toe"));

        assertTrue(output.contains("Guess the hidden five-letter word in six attempts"));
        assertTrue(output.contains("Solve randomly generated math equations"));
        assertTrue(output.contains("Locate and sink all enemy ships to win"));
        assertTrue(output.contains("Get three marks in a row to win"));
    }

    @Test
    void listIsCaseInsensitive() {
        String output = run("LIST", "exit");

        assertTrue(output.contains("[ID: 1] Wordle"));
    }

    @Test
    void listLettersOutsideWordleShowsError() {
        String output = run("list letters", "exit");

        assertTrue(output.contains("The 'list letters' command only works while playing Wordle."));
    }

    @Test
    void listLettersInWordleShowsHintCategories() {
        String output = run("play 1", "CREAM", "list letters", "exit");

        assertTrue(output.contains("Letters with correct positions:"));
        assertTrue(output.contains("Letters in incorrect positions:"));
        assertTrue(output.contains("Letters not in the word:"));
    }

    @Test
    void helpListsWordleCommandOnlyDuringWordle() {
        String outsideWordle = run("help", "exit");
        String inWordle = run("play 1", "help", "exit");

        assertFalse(outsideWordle.contains("list letters"));
        assertTrue(inWordle.contains("list letters  - Show discovered Wordle letter hints"));
    }

    @Test
    void unknownCommandShowsError() {
        String output = run("dance", "exit");

        assertTrue(output.contains("Unknown command: dance"));
    }

    @Test
    void playInvalidIdShowsGameList() {
        String output = run("play 9", "exit");

        assertTrue(output.contains("Available games:"));
    }

    @Test
    void quitOutsideGameShowsError() {
        String output = run("quit", "exit");

        assertTrue(output.contains("The 'quit' command only works while playing a game."));
    }

    @Test
    void playWordleThenQuitReturnsToMenu() {
        String output = run("play 1", "quit", "exit");

        assertTrue(output.contains("Starting Wordle..."));
        assertTrue(output.contains("Returning to the main menu."));
        assertTrue(output.contains("Bye!"));
    }

    @Test
    void startingTicTacToeShowsInstructionsAndSwitchingShowsExitMessage() {
        String output = run("play 4", "play 1", "exit");

        assertTrue(output.contains("Starting Tic Tac Toe..."));
        assertTrue(output.contains("You are X; the computer is O."));
        assertTrue(output.contains("Enter a square from A1 to C3"));
        assertTrue(output.contains("Returning to the main menu."));
        assertTrue(output.contains("Starting Wordle..."));
    }

    @Test
    void inputEndingWithoutExitSaysBye() {
        // No "exit" line: main should notice the input has run out and close cleanly.
        String output = run("help");

        assertTrue(output.contains("Bye!"));
    }
}
