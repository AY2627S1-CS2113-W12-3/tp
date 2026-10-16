package seedu.duke;

import java.util.Random;

/**
 * A mental-arithmetic drill. Each round presents one randomly generated problem,
 * the player types the answer, and feedback is immediate.
 *
 * <p>One call to {@link #handleInput(String)} handles exactly one line of input and
 * returns exactly one response. The method never loops and never reads input itself,
 * so all state that has to survive between rounds -- the current problem, the score,
 * the round number -- is held in fields. Keeping the game passive like this is what
 * lets the application stay at a single command prompt instead of handing the
 * terminal over to the game.
 */
public class CalculatorGame implements Game {
    /** Indent for the problem line and for feedback, matching the user guide. */
    private static final String DETAIL_INDENT = "    ";

    /** Indent for the round header, one level shallower than the problem it heads. */
    private static final String HEADER_INDENT = "  ";

    /** Identifies this game in the application. */
    private final String gameId;

    private final String gameName = "Calculator";

    /** Source of randomness for problem generation, injectable so tests can seed it. */
    private final Random random;

    /** The problem the player is currently answering. */
    private ArithmeticProblem currentProblem;

    /** The round being played, counting from 1. */
    private int roundNumber = 1;

    /** How many rounds the player has answered correctly. */
    private int correctCount = 0;

    /**
     * How many rounds the player has actually answered. Input that was not a whole
     * number is not an attempt, so this can lag behind the number of lines typed.
     */
    private int attemptedCount = 0;

    /** Set once the player leaves the session, which ends the game. */
    private boolean finished = false;

    /**
     * Creates a calculator game with the given ID and an unseeded random source.
     *
     * @param gameId the ID used to select this game
     */
    public CalculatorGame(String gameId) {
        this(gameId, new Random());
    }

    /**
     * Creates a calculator game with a caller-supplied random source.
     *
     * <p>Tests use this to seed generation and get repeatable problems.
     *
     * @param gameId the ID used to select this game
     * @param random the source of randomness for problem generation
     */
    public CalculatorGame(String gameId, Random random) {
        this.gameId = gameId;
        this.random = random;
        this.currentProblem = ArithmeticProblem.generate(random);
    }

    @Override
    public String getGameId() {
        return gameId;
    }

    @Override
    public String getGameName() {
        return gameName;
    }

    /**
     * Returns the opening text: how to leave the game, and the first problem.
     *
     * <p>Without this the player would face a bare prompt with nothing to answer,
     * because the main loop reads a line before it calls into the game.
     *
     * @return the intro line followed by the first problem
     */
    @Override
    public String getStartMessage() {
        return HEADER_INDENT + "calculator - type 'quit' to stop." + System.lineSeparator()
                + System.lineSeparator()
                + buildRoundPrompt();
    }

    /**
     * Builds the header and problem that open a round, for example:
     *
     * <pre>
     *   Round 3  |  Score: 1/2
     *     72 / 8 = ?
     * </pre>
     *
     * @return the round header followed by the current problem
     */
    private String buildRoundPrompt() {
        return HEADER_INDENT + "Round " + roundNumber
                + "  |  Score: " + correctCount + "/" + attemptedCount
                + System.lineSeparator()
                + DETAIL_INDENT + currentProblem;
    }

    /**
     * Grades one answer and moves on to the next problem.
     *
     * @param input the line the player typed
     * @return feedback on the answer, followed by the next problem
     */
    @Override
    public String handleInput(String input) {
        Integer answer = parseWholeNumber(input);
        if (answer == null) {
            // Refined in the issue covering non-numeric input.
            return DETAIL_INDENT + "That is not a whole number."
                    + System.lineSeparator() + DETAIL_INDENT + currentProblem;
        }

        String feedback;
        attemptedCount++;
        if (answer == currentProblem.getAnswer()) {
            correctCount++;
            feedback = DETAIL_INDENT + "Correct!";
        } else {
            // Always reveal the answer, so a wrong round still teaches something.
            feedback = DETAIL_INDENT + "Not quite - the answer was "
                    + currentProblem.getAnswer() + ".";
        }
        assert correctCount <= attemptedCount : "Correct answers cannot exceed attempts";

        roundNumber++;
        currentProblem = ArithmeticProblem.generate(random);
        return feedback + System.lineSeparator()
                + System.lineSeparator()
                + buildRoundPrompt();
    }

    /**
     * Parses a whole number, allowing an optional leading minus sign.
     *
     * @param input the line the player typed
     * @return the parsed value, or {@code null} if the input is not a whole number
     */
    private static Integer parseWholeNumber(String input) {
        if (input == null) {
            return null;
        }
        String trimmed = input.trim();
        if (!trimmed.matches("-?\\d+")) {
            return null;
        }
        try {
            return Integer.valueOf(trimmed);
        } catch (NumberFormatException e) {
            // Digits that overflow an int, such as a very long run of 9s.
            return null;
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void reset() {
        finished = false;
        roundNumber = 1;
        correctCount = 0;
        attemptedCount = 0;
        currentProblem = ArithmeticProblem.generate(random);
    }

    /**
     * Returns how many rounds the player has answered.
     *
     * @return the number of attempted rounds
     */
    public int getAttemptedCount() {
        return attemptedCount;
    }

    /**
     * Returns how many rounds the player has answered correctly.
     *
     * @return the number of correct answers
     */
    public int getCorrectCount() {
        return correctCount;
    }
}
