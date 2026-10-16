package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Tests the number of attempts permitted by a Wordle game session. */
class WordleGameTest {

    @Test
    void fiveIncorrectGuessesFinishTheGame() {
        WordleGame game = new WordleGame("1");

        for (int attempt = 1; attempt < 5; attempt++) {
            game.handleInput("WRONG");
            assertFalse(game.isFinished());
        }

        game.handleInput("WRONG");

        assertTrue(game.isFinished());
    }

    @Test
    void resetRestoresAllFiveAttempts() {
        WordleGame game = new WordleGame("1");
        for (int attempt = 0; attempt < 5; attempt++) {
            game.handleInput("WRONG");
        }

        game.reset();

        assertFalse(game.isFinished());
        for (int attempt = 1; attempt < 5; attempt++) {
            game.handleInput("WRONG");
            assertFalse(game.isFinished());
        }
    }

    @Test
    void quitCommandFinishesTheGameAndReturnsToMenu() {
        WordleGame game = new WordleGame("1");

        String response = game.handleInput("quit");

        assertEquals("Returning to the main menu.", response);
        assertTrue(game.isFinished());
    }

    @Test
    void wrongGuessShowsTriesLeft() {
        WordleGame game = new WordleGame("1");

        assertEquals("Wrong guess! You have 4 tries left.", game.handleInput("WRONG"));
        game.handleInput("WRONG");
        game.handleInput("WRONG");
        assertEquals("Wrong guess! You have 1 try left.", game.handleInput("WRONG"));
    }

    @Test
    void lastWrongGuessSaysOutOfTriesAndReturnsToMenu() {
        WordleGame game = new WordleGame("1");
        for (int attempt = 1; attempt < 5; attempt++) {
            game.handleInput("WRONG");
        }

        String response = game.handleInput("WRONG");

        assertEquals("Wrong guess! You are out of tries." + System.lineSeparator()
                + "Returning to the main menu.", response);
    }

    @Test
    void correctGuessShowsTriesTakenAndReturnsToMenu() {
        WordleGame game = new WordleGame("1");
        game.handleInput("WRONG");
        game.handleInput("WRONG");

        String response = game.handleInput("crane");

        assertEquals("Correct guess! You got it in 3 tries." + System.lineSeparator()
                + "Returning to the main menu.", response);
        assertTrue(game.isFinished());
    }

    @Test
    void correctFirstGuessUsesSingularTry() {
        WordleGame game = new WordleGame("1");

        String response = game.handleInput("CRANE");

        assertEquals("Correct guess! You got it in 1 try." + System.lineSeparator()
                + "Returning to the main menu.", response);
    }

    @Test
    void quitCommandIsCaseInsensitive() {
        WordleGame game = new WordleGame("1");

        game.handleInput("QUIT");

        assertTrue(game.isFinished());
    }
}
