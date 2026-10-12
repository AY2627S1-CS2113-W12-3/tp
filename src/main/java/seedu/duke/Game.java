package seedu.duke;

/**
 * Defines the common operations the application can use to interact with a game.
 */
public interface Game {

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
}
