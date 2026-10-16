package seedu.duke;

/**
 * Provides the basic Wordle game type while gameplay is still to be implemented.
 */
public class WordleGame implements Game {
    /**
     * Identifies this game in the application.
     */
    private final String gameId;
    private final String gameName = "Wordle";
    private final String correctGuess = "Correct guess!";
    private final String wrongGuess = "Wrong guess!";
    private boolean finished = false;
    private String[] guesses = {"WORDS", "CRANE", "AUDIO", "CRYPT", "DREAM"};

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
     * Returns a placeholder response until Wordle gameplay is implemented.
     *
     * @param input the input entered by the player
     * @return a message indicating gameplay is not implemented yet
     */
    @Override
    public String handleInput(String input) {
        for (String guess : guesses) {
            // compareToIgnoreCase returns 0 if the guesses are equal
            if (input.compareToIgnoreCase(guess) == 0) {
                finished = true;
                return correctGuess;
            }
        }
        finished = true;
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
    }
}
