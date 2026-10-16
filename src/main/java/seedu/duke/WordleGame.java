package seedu.duke;

/**
 * Provides a Wordle-style game with up to five guesses per session.
 */
public class WordleGame implements Game {
    /** The number of incorrect guesses allowed before a session ends. */
    private static final int MAX_ATTEMPTS = 5;
    /** Command used to leave a Wordle session and return to the main menu. */
    private static final String QUIT_COMMAND = "quit";
    /**
     * Identifies this game in the application.
     */
    private final String gameId;
    private final String gameName = "Wordle";
    private final String correctGuess = "Correct guess!";
    private final String wrongGuess = "Wrong guess!";
    private final String exitGameMessage = "Returning to the main menu.";
    /** Tracks whether the current game session has ended. */
    private boolean finished = false;
    /** Counts incorrect guesses in the current game session. */
    private int attemptsUsed = 0;
    /** Lists words that count as a correct guess in this starter implementation. */
    private final String[] guesses = {"WORDS", "CRANE", "AUDIO", "CRYPT", "DREAM"};

    public WordleGame(String gameId) {
        this.gameId = gameId;
    }

    @Override
    public String getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    /**
     * Tells the player how many tries they have and how to leave, so they know
     * the rules before typing their first guess.
     *
     * @return the opening instructions
     */
    @Override
    public String getStartMessage() {
        return "Guess the word. You have " + formatTries(MAX_ATTEMPTS)
                + ". Type 'quit' to leave.";
    }

    /**
     * Checks a submitted word or processes the {@code quit} command. A correct
     * word ends the game immediately; otherwise, the game ends after five incorrect guesses.
     * Whenever the game ends, the response says so, because the application
     * returns to the main menu straight afterwards.
     *
     * @param input the word entered by the player
     * @return feedback about the submitted word or game exit
     */
    @Override
    public String handleInput(String input) {
        if (input.equalsIgnoreCase(QUIT_COMMAND)) {
            finished = true;
            return exitGameMessage;
        }

        for (String guess : guesses) {
            // compareToIgnoreCase returns 0 if the guesses are equal
            if (input.compareToIgnoreCase(guess) == 0) {
                finished = true;
                // attemptsUsed only counts wrong guesses, so add this correct one.
                int triesTaken = attemptsUsed + 1;
                return correctGuess + " You got it in " + formatTries(triesTaken) + "."
                        + System.lineSeparator() + exitGameMessage;
            }
        }
        attemptsUsed++;
        // The game finishes on the last allowed guess, so it is never asked to grade another.
        assert attemptsUsed <= MAX_ATTEMPTS : "Wrong guesses cannot exceed the limit";
        int triesLeft = MAX_ATTEMPTS - attemptsUsed;
        if (triesLeft == 0) {
            finished = true;
            return wrongGuess + " You are out of tries."
                    + System.lineSeparator() + exitGameMessage;
        }
        return wrongGuess + " You have " + formatTries(triesLeft) + " left.";
    }

    /**
     * Formats a number of tries with the correct singular or plural noun.
     *
     * @param count the number of tries
     * @return for example "1 try" or "3 tries"
     */
    private static String formatTries(int count) {
        assert count >= 0 : "Number of tries cannot be negative";
        return count + (count == 1 ? " try" : " tries");
    }

    /**
     * Reports whether the current game has finished.
     *
     * @return true if game is finished, false otherwise
     */
    @Override
    public boolean isFinished() {
        return finished;
    }

    /**
     * Changes the status of completion back to uncompleted so that replay is possible
     */
    @Override
    public void reset() {
        finished = false;
        attemptsUsed = 0;
    }
}
