package seedu.duke;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.duke.games.battleship.BattleshipGame;
import seedu.duke.games.calculator.CalculatorGame;
import seedu.duke.games.gta.GtaGame;
import seedu.duke.games.tictactoe.TicTacToeGame;
import seedu.duke.games.wordle.WordleGame;

/**
 * Stores the games available in the application.
 */
public class GameList {
    /** Records diagnostic events without changing the user-facing output. */
    private static final Logger LOGGER = Logger.getLogger(GameList.class.getName());
    /** The games and their IDs, available through each game's getters. */
    private final ArrayList<Game> games;

    /**
     * Creates the game list with Wordle as game 1, the calculator as game 2,
     * Battleship as game 3,Tic Tac Toe as game 4, GTA -6 as game 5
     */
    public GameList() {
        games = new ArrayList<>();
        games.add(new WordleGame("1"));
        games.add(new CalculatorGame("2"));
        games.add(new BattleshipGame("3"));
        games.add(new TicTacToeGame("4"));
        games.add(new GtaGame("5"));
        assert !games.isEmpty() : "Game list must not be empty";
        LOGGER.fine("Registered " + games.size() + " games.");
    }

    /** Creates a game list from supplied games for controlled test setups. */
    GameList(List<Game> games) {
        this.games = new ArrayList<>(games);
        assert !this.games.isEmpty() : "Game list must not be empty";
        LOGGER.fine("Registered " + this.games.size() + " games.");
    }

    /**
     * Returns the games currently in the list.
     *
     * @return an unmodifiable list of the available games
     */
    public List<Game> getGames() {
        return List.copyOf(games);
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
