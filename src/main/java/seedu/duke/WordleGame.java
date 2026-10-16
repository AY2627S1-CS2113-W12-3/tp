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
     * Checks a submitted word or processes the {@code quit} command. A correct
     * word ends the game immediately; otherwise, the game ends after five incorrect guesses.
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
                return correctGuess;
            }
        }
        attemptsUsed++;
        if (attemptsUsed == MAX_ATTEMPTS) {
            finished = true;
        }
        return wrongGuess;
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
