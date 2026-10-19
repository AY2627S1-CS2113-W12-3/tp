package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.duke.wordle.WordleGame;

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
    void invalidGuessDoesNotConsumeAnAttempt() {
        WordleGame game = new WordleGame("1");

        assertEquals("Please enter a five-letter word.", game.handleInput("CAT"));
        for (int attempt = 1; attempt < 5; attempt++) {
            game.handleInput("WRONG");
            assertFalse(game.isFinished());
        }

        game.handleInput("WRONG");

        assertTrue(game.isFinished());
    }

    @Test
    void repeatedGuessDoesNotConsumeAnAttempt() {
        WordleGame game = new WordleGame("1");

        game.handleInput("WRONG");
        assertEquals("You have already guessed that word. Try a different five-letter word.",
                game.handleInput("wrong"));

        game.handleInput("ALPHA");
        game.handleInput("BRAVO");
        game.handleInput("DELTA");
        assertFalse(game.isFinished());

        game.handleInput("EAGLE");
        assertTrue(game.isFinished());
    }

    @Test
    void onlyTheSecretWordWins() {
        WordleGame game = new WordleGame("1");

        assertTrue(game.handleInput("WORDS").contains("Wrong guess! You have 4 tries left."));
        assertFalse(game.isFinished());
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

        assertTrue(game.handleInput("WRONG").contains("Wrong guess! You have 4 tries left."));
        game.handleInput("WRONG");
        game.handleInput("WRONG");
        assertTrue(game.handleInput("WRONG").contains("Wrong guess! You have 1 try left."));
    }

    @Test
    void lastWrongGuessSaysOutOfTriesAndReturnsToMenu() {
        WordleGame game = new WordleGame("1");
        for (int attempt = 1; attempt < 5; attempt++) {
            game.handleInput("WRONG");
        }

        String response = game.handleInput("WRONG");

        assertTrue(response.contains("Wrong guess! You are out of tries."));
        assertTrue(response.contains("Returning to the main menu."));
    }

    @Test
    void correctGuessShowsTriesTakenAndReturnsToMenu() {
        WordleGame game = new WordleGame("1");
        game.handleInput("WRONG");
        game.handleInput("WRONG");

        String response = game.handleInput("crane");

        assertTrue(response.contains("Correct guess! You got it in 3 tries."));
        assertTrue(response.contains("Returning to the main menu."));
        assertTrue(game.isFinished());
    }

    @Test
    void correctFirstGuessUsesSingularTry() {
        WordleGame game = new WordleGame("1");

        String response = game.handleInput("CRANE");

        assertTrue(response.contains("Correct guess! You got it in 1 try."));
        assertTrue(response.contains("Returning to the main menu."));
    }

    @Test
    void quitCommandIsCaseInsensitive() {
        WordleGame game = new WordleGame("1");

        game.handleInput("QUIT");

        assertTrue(game.isFinished());
    }

    @Test
    void feedbackMarksExactMisplacedAndAbsentLetters() {
        WordleGame game = new WordleGame("1");

        String response = game.handleInput("CREAM");

        assertTrue(response.startsWith("\u001B[32mC\u001B[0m\u001B[32mR\u001B[0m"
                + "\u001B[33mE\u001B[0m\u001B[33mA\u001B[0m\u001B[31mM\u001B[0m"));
    }

    @Test
    void listLettersBeforeAnyGuessShowsError() {
        WordleGame game = new WordleGame("1");

        assertEquals("Make a valid Wordle guess before listing letters.", game.handleInput("list letters"));
    }
}
