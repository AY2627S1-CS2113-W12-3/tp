package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the state transitions and feedback of a Wordle game.
 */
class WordleGameTest {

    @Test
    public void handleInput_correctGuess_finishesGame() {
        WordleGame game = new WordleGame("apple");

        String feedback = game.handleInput("apple");

        assertTrue(game.isFinished());
        assertTrue(feedback.contains("You solved it in 1 attempt(s)!"));
        assertTrue(feedback.contains("\u001B[32mA"));
    }

    @Test
    public void handleInput_invalidLengthGuess_doesNotFinishGame() {
        WordleGame game = new WordleGame("apple");

        String feedback = game.handleInput("four");

        assertFalse(game.isFinished());
        assertTrue(feedback.contains("Enter a five-letter word."));
    }

    @Test
    public void handleInput_fiveWrongGuesses_finishesGame() {
        WordleGame game = new WordleGame("apple");

        for (int attempt = 0; attempt < 4; attempt++) {
            game.handleInput("crane");
            assertFalse(game.isFinished());
        }
        String feedback = game.handleInput("crane");

        assertTrue(game.isFinished());
        assertTrue(feedback.contains("No attempts left. The word was APPLE."));
    }
}
