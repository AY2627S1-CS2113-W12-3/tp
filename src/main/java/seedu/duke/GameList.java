package seedu.duke;

import java.util.ArrayList;

/**
 * Stores the games available in the application.
 */
public class GameList {
    /** The games and their IDs, available through each game's getters. */
    private final ArrayList<Game> games;

    /**
     * Creates the game list with Wordle registered as game 1.
     */
    public GameList() {
        games = new ArrayList<>();
        games.add(new WordleGame("1"));
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
