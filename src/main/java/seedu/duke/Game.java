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
}
