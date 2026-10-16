package seedu.duke;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Stores the games available in the application.
 */
public class GameList {
    /** Records diagnostic events without changing the user-facing output. */
    private static final Logger LOGGER = Logger.getLogger(GameList.class.getName());
    /** The games and their IDs, available through each game's getters. */
    private final ArrayList<Game> games;

    /**
     * Creates the game list with Wordle as game 1 and the calculator as game 2.
     */
    public GameList() {
        games = new ArrayList<>();
        games.add(new WordleGame("1"));
        games.add(new CalculatorGame("2"));
        assert !games.isEmpty() : "Game list must not be empty";
        LOGGER.fine("Registered " + games.size() + " games.");
    }

    /**
     * Returns the games currently in the list.
     *
     * @return the list of games
     */
    public ArrayList<Game> getGames() {
        return games;
    }
}
