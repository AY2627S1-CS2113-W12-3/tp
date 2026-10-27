package seedu.duke;

import java.util.ArrayList;

/**
 * Stores the games available in the application.
 */
public class GameList {
    /** The games and their IDs, available through each game's getters. */
    private final ArrayList<Game> games;

    /**
     * Creates the game list with Wordle as game 1, the calculator as game 2, and GTA -6 as game 3.
     */
    public GameList() {
        games = new ArrayList<>();
        games.add(new WordleGame("1"));
        games.add(new CalculatorGame("2"));
        games.add(new GtaGame("3"));
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
