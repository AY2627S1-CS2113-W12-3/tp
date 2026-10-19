package seedu.duke;

import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.duke.wordle.WordleGame;

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

    /**
     * Finds the game with the given ID.
     *
     * <p>Keeping the lookup here rather than in the command loop means the loop does
     * not need to know how games are stored.
     *
     * @param gameId the ID to look for, as typed by the player
     * @return the matching game, or {@code null} if no game has that ID
     */
    public Game findGameById(String gameId) {
        if (gameId == null) {
            return null;
        }
        for (Game game : games) {
            if (game.getGameId().equals(gameId.trim())) {
                return game;
            }
        }
        return null;
    }
}
