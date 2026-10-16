package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

/**
 * Tests the calculator game's grading, scoring, and session handling.
 *
 * <p>Every test seeds its own {@link Random} so problem generation is repeatable.
 * The answer to the current problem is not hard-coded; it is read back from the
 * game, which keeps the tests valid if the seeded sequence ever changes.
 */
class CalculatorGameTest {

    /** Creates a game with a fixed seed so its problems are repeatable. */
    private CalculatorGame newGame() {
        return new CalculatorGame("2", new Random(2024));
    }

    /**
     * Answers the current problem correctly by reading the expected answer out of
     * the start message, which is the only place the problem is exposed as text.
     *
     * <p>The problem is regenerated after each graded answer, so this has to be
     * re-derived every round rather than cached.
     */
    private static int correctAnswerFor(CalculatorGame game) {
        // Rebuild the problem text and solve it, mirroring what a player reads.
        String prompt = game.getStartMessage();
        String problemLine = prompt.substring(prompt.lastIndexOf(System.lineSeparator())).trim();
        String[] parts = problemLine.replace(" = ?", "").split(" ");
        int left = Integer.parseInt(parts[0]);
        String operator = parts[1];
        int right = Integer.parseInt(parts[2]);
        return switch (operator) {
        case "+" -> left + right;
        case "-" -> left - right;
        case "x" -> left * right;
        case "/" -> left / right;
        default -> throw new IllegalStateException("Unknown operator: " + operator);
        };
    }

    @Test
    public void getStartMessageShowsTheFirstRoundAndProblem() {
        CalculatorGame game = newGame();
        String start = game.getStartMessage();
        assertTrue(start.contains("Round 1"), "Start message should open at round 1");
        assertTrue(start.contains("Score: 0/0"), "Start message should open at a zero score");
        assertTrue(start.contains("= ?"), "Start message should include a problem");
    }

    @Test
    public void handleInputCorrectAnswerIncrementsBothCounters() {
        CalculatorGame game = newGame();
        String response = game.handleInput(String.valueOf(correctAnswerFor(game)));
        assertTrue(response.contains("Correct!"));
        assertEquals(1, game.getCorrectCount());
        assertEquals(1, game.getAttemptedCount());
    }

    @Test
    public void handleInputWrongAnswerCountsTheAttemptAndRevealsTheAnswer() {
        CalculatorGame game = newGame();
        int correct = correctAnswerFor(game);
        // Offsetting by one guarantees a wrong answer whatever the problem is.
        String response = game.handleInput(String.valueOf(correct + 1));
        assertTrue(response.contains("Not quite"), "Wrong answers should say so");
        assertTrue(response.contains(String.valueOf(correct)),
                "Wrong answers should reveal the correct answer");
        assertEquals(0, game.getCorrectCount());
        assertEquals(1, game.getAttemptedCount());
    }

    @Test
    public void handleInputNonNumericLeavesTheScoreUntouched() {
        CalculatorGame game = newGame();
        String response = game.handleInput("nine");
        assertTrue(response.contains("not a whole number"));
        assertTrue(response.contains("does not count"));
        assertEquals(0, game.getCorrectCount());
        assertEquals(0, game.getAttemptedCount());
    }

    @Test
    public void handleInputNonNumericReAsksTheSameProblem() {
        CalculatorGame game = newGame();
        int answerBefore = correctAnswerFor(game);
        game.handleInput("not a number");
        assertEquals(answerBefore, correctAnswerFor(game),
                "A rejected answer should leave the same problem in play");
    }

    @Test
    public void handleInputAcceptsNegativeAnswers() {
        CalculatorGame game = newGame();
        // Negative input is never correct given the documented ranges, but it must
        // be graded as an answer rather than rejected as malformed.
        game.handleInput("-7");
        assertEquals(1, game.getAttemptedCount(),
                "A negative whole number is a valid answer and counts as an attempt");
    }

    @Test
    public void handleInputQuitFinishesTheGameAndSummarisesTheSession() {
        CalculatorGame game = newGame();
        game.handleInput(String.valueOf(correctAnswerFor(game)));
        String summary = game.handleInput("quit");
        assertTrue(game.isFinished(), "quit should end the game");
        assertTrue(summary.contains("Session summary"));
        assertTrue(summary.contains("Rounds attempted : 1"));
        assertTrue(summary.contains("Correct          : 1"));
        assertTrue(summary.contains("Accuracy         : 100%"));
    }

    @Test
    public void handleInputQuitIsCaseInsensitive() {
        CalculatorGame game = newGame();
        game.handleInput("QUIT");
        assertTrue(game.isFinished(), "quit should be recognised regardless of case");
    }

    @Test
    public void handleInputQuitDoesNotCountTheRoundInProgress() {
        CalculatorGame game = newGame();
        String summary = game.handleInput("quit");
        assertTrue(summary.contains("Rounds attempted : 0"),
                "Quitting before answering should report no attempts");
    }

    @Test
    public void accuracyIsADashWhenNoRoundsWereAttempted() {
        CalculatorGame game = newGame();
        String summary = game.handleInput("quit");
        assertTrue(summary.contains("Accuracy         : -"),
                "Accuracy should be a dash, not 0%, when nothing was attempted");
    }

    @Test
    public void accuracyIsRoundedToTheNearestWholePercent() {
        CalculatorGame game = newGame();
        // One correct out of three is 33.33%, which must round down to 33%.
        game.handleInput(String.valueOf(correctAnswerFor(game)));
        game.handleInput(String.valueOf(correctAnswerFor(game) + 1));
        game.handleInput(String.valueOf(correctAnswerFor(game) + 1));
        String summary = game.handleInput("quit");
        assertTrue(summary.contains("Accuracy         : 33%"),
                "1 of 3 should report as 33%, but got: " + summary);
    }

    @Test
    public void resetClearsTheScoreAndRoundNumber() {
        CalculatorGame game = newGame();
        game.handleInput(String.valueOf(correctAnswerFor(game)));
        game.handleInput("quit");
        game.reset();
        assertFalse(game.isFinished(), "reset should make the game playable again");
        assertEquals(0, game.getCorrectCount());
        assertEquals(0, game.getAttemptedCount());
        assertTrue(game.getStartMessage().contains("Round 1"),
                "reset should return the game to round 1");
    }

    @Test
    public void getGameIdAndNameAreReported() {
        CalculatorGame game = newGame();
        assertEquals("2", game.getGameId());
        assertEquals("Calculator", game.getGameName());
    }
}
