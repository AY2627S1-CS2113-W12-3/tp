package seedu.duke;

/**
 * Defines the common operations the application can use to interact with a game.
 */
public interface Game {

    /**
     * Returns the ID used to select this game.
     *
     * @return this game's ID
     */
    String getGameId();

    /**
     * Returns the name of the game
     *
     * @return this game's name
     */
    String getGameName();

    /**
     * Returns the text to display when the game starts, before any input is read.
     *
     * <p>Some games need to show something before the player can sensibly type
     * anything -- the calculator, for example, must show the first problem.
     * Games that have nothing to say up front can ignore this method, because
     * the default implementation returns an empty string.
     *
     * @return the opening text, or an empty string if the game has none
     */
    default String getStartMessage() {
        return "";
    }

    /**
     * Returns the text to display when the player leaves this game.
     *
     * <p>The counterpart to {@link #getStartMessage()}. It lets the launcher report
     * a closing message -- the calculator's session summary, for example -- when the
     * player is taken out of a game by a launcher command rather than by the game
     * itself. Games with nothing to report can ignore it.
     *
     * @return the closing text, or an empty string if the game has none
     */
    default String getExitMessage() {
        return "";
    }

    /**
     * Processes one line of player input and returns a response for the application to display.
     *
     * @param input the input entered by the player
     * @return the response produced by the game
     */
    String handleInput(String input);

    /**
     * Checks whether the game has finished.
     *
     * @return true if the game has finished, otherwise false
     */
    boolean isFinished();

    /**
     * Changes the status of completion back to uncompleted so that replay is possible
     */
    void reset();

    /**
     * Returns the commands specifically belonging to the game
     */
     default String getCommands() {
        return "";
    }

    /**
     * Returns a short description of the game
     */
    String getDescription();
}
