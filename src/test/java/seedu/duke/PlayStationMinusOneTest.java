package seedu.duke;

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

        assertTrue(output.contains("Wordle | ID: 1"));
        assertTrue(output.contains("Calculator | ID: 2"));
    }

    @Test
    void listIsCaseInsensitive() {
        String output = run("LIST", "exit");

        assertTrue(output.contains("Wordle | ID: 1"));
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
    void inputEndingWithoutExitSaysBye() {
        // No "exit" line: main should notice the input has run out and close cleanly.
        String output = run("help");

        assertTrue(output.contains("Bye!"));
    }
}
