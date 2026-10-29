package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.duke.wordle.WordleGame;

/** Tests the number of attempts permitted by a Wordle game session. */
class WordleGameTest {
    /** Five distinct guesses that are not the current secret word, CRANE. */
    private static final String[] INCORRECT_GUESSES = {"WRONG", "ALPHA", "BRAVO", "DELTA", "EAGLE"};

    @Test
    void fiveIncorrectGuessesFinishTheGame() {
        WordleGame game = createGame();

        for (int attempt = 0; attempt < INCORRECT_GUESSES.length - 1; attempt++) {
            game.handleInput(INCORRECT_GUESSES[attempt]);
            assertFalse(game.isFinished());
        }

        game.handleInput(INCORRECT_GUESSES[INCORRECT_GUESSES.length - 1]);

        assertTrue(game.isFinished());
        assertEquals(0, game.getPointsEarnedForLastAction());
    }

    @Test
    void invalidGuessDoesNotConsumeAnAttempt() {
        WordleGame game = createGame();

        assertEquals("Please enter a five-letter word.", game.handleInput("CAT"));
        assertEquals(0, game.getPointsEarnedForLastAction());
        for (int attempt = 0; attempt < INCORRECT_GUESSES.length - 1; attempt++) {
            game.handleInput(INCORRECT_GUESSES[attempt]);
            assertFalse(game.isFinished());
        }

        game.handleInput(INCORRECT_GUESSES[INCORRECT_GUESSES.length - 1]);

        assertTrue(game.isFinished());
    }

    @Test
    void repeatedGuessDoesNotConsumeAnAttempt() {
        WordleGame game = createGame();

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
        WordleGame game = createGame();

        assertTrue(game.handleInput("WORDS").contains("Wrong guess! You have 4 tries left."));
        assertFalse(game.isFinished());
    }

    @Test
    void resetRestoresAllFiveAttempts() {
        WordleGame game = createGame();
        for (String guess : INCORRECT_GUESSES) {
            game.handleInput(guess);
        }

        game.reset();

        assertFalse(game.isFinished());
        for (int attempt = 0; attempt < INCORRECT_GUESSES.length - 1; attempt++) {
            game.handleInput(INCORRECT_GUESSES[attempt]);
            assertFalse(game.isFinished());
        }
        game.handleInput(INCORRECT_GUESSES[INCORRECT_GUESSES.length - 1]);
        assertTrue(game.isFinished());
    }

    @Test
    void quitCommandFinishesTheGameAndReturnsToMenu() {
        WordleGame game = createGame();

        String response = game.handleInput("quit");

        assertEquals("Returning to the main menu.", response);
        assertTrue(game.isFinished());
    }

    @Test
    void wrongGuessShowsTriesLeft() {
        WordleGame game = createGame();

        assertTrue(game.handleInput("WRONG").contains("Wrong guess! You have 4 tries left."));
        game.handleInput("ALPHA");
        game.handleInput("BRAVO");
        assertTrue(game.handleInput("DELTA").contains("Wrong guess! You have 1 try left."));
    }

    @Test
    void lastWrongGuessSaysOutOfTriesAndReturnsToMenu() {
        WordleGame game = createGame();
        for (int attempt = 0; attempt < INCORRECT_GUESSES.length - 1; attempt++) {
            game.handleInput(INCORRECT_GUESSES[attempt]);
        }

        String response = game.handleInput(INCORRECT_GUESSES[INCORRECT_GUESSES.length - 1]);

        assertTrue(response.contains("Wrong guess! You are out of tries."));
        assertTrue(response.contains("The word was CRANE."));
        assertTrue(response.contains("Returning to the main menu."));
    }

    @Test
    void correctGuessShowsTriesTakenAndReturnsToMenu() {
        WordleGame game = createGame();
        game.handleInput("WRONG");
        game.handleInput("ALPHA");

        String response = game.handleInput("crane");

        assertTrue(response.contains("Correct guess! You completed Wordle in 3 tries."));
        assertTrue(response.contains("Returning to the main menu."));
        assertTrue(game.isFinished());
        assertEquals(5, game.getPointsEarnedForLastAction());
    }

    @Test
    void correctFirstGuessUsesSingularTry() {
        WordleGame game = createGame();

        String response = game.handleInput("CRANE");

        assertTrue(response.contains("Correct guess! You completed Wordle in 1 try."));
        assertTrue(response.contains("Returning to the main menu."));
        assertEquals(5, game.getPointsEarnedForLastAction());
    }

    @Test
    void quitCommandIsCaseInsensitive() {
        WordleGame game = createGame();

        game.handleInput("QUIT");

        assertTrue(game.isFinished());
        assertEquals(0, game.getPointsEarnedForLastAction());
    }

    @Test
    void feedbackMarksExactMisplacedAndAbsentLetters() {
        WordleGame game = createGame();

        String response = game.handleInput("CREAM");

        assertTrue(response.startsWith("\u001B[32mC\u001B[0m\u001B[32mR\u001B[0m"
                + "\u001B[33mE\u001B[0m\u001B[33mA\u001B[0m\u001B[31mM\u001B[0m"));
    }

    @Test
    void listLettersBeforeAnyGuessShowsError() {
        WordleGame game = createGame();

        assertEquals("Make a valid Wordle guess before listing letters.", game.handleInput("list letters"));
    }

    /** Creates a predictable game so these tests do not depend on random word selection. */
    private static WordleGame createGame() {
        return new WordleGame("1", "CRANE");
    }
}
