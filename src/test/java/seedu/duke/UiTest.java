package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Tests the text that Ui prints to the console. */
class UiTest {
    /** The real console, saved so it can be put back after each test. */
    private PrintStream originalOut;
    /** Collects everything printed during a test so it can be checked. */
    private ByteArrayOutputStream output;

    /**
     * Redirects System.out into a buffer. Ui prints with System.out.println,
     * so this is how a test can "see" what would have appeared on screen.
     */
    @BeforeEach
    void captureOutput() {
        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    @Test
    void showHelpListsListCommand() {
        new Ui().showHelp();

        // contains() rather than equals() avoids depending on \n vs \r\n line endings.
        assertTrue(output.toString().contains("list  - Show all games and their IDs"));
    }

    @Test
    void showHelpListsQuitCommand() {
        new Ui().showHelp();

        assertTrue(output.toString().contains("quit  - Leave the current game"));
    }

    @Test
    void showHelpListsScoreCommand() {
        new Ui().showHelp();

        assertTrue(output.toString().contains("score  - Show your saved points"));
    }

    @Test
    void showHelpOutsideWordleDoesNotListWordleCommands() {
        new Ui().showHelp();

        assertFalse(output.toString().contains("list letters"));
    }

    @Test
    void showHelpInWordleListsWordleCommands() {
        new Ui().showHelp("list letters  - Show discovered Wordle letter hints\n");

        assertTrue(output.toString().contains("list letters  - Show discovered Wordle letter hints"));
    }

    @Test
    void showListOfGamesPrintsEveryGame() {
        new Ui().showListOfGames(new GameList());

        String printed = output.toString();
        assertTrue(printed.contains("[ID: 1] Wordle"));
        assertTrue(printed.contains("[ID: 2] Calculator"));
    }
}
