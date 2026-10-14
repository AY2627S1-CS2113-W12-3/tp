package seedu.duke;

/**
 * Provides the base type for Wordle games.
 *
 * <p>Concrete subclasses must implement the operations declared by {@link Game}.</p>
 */
public abstract class WordleGame implements Game {
    /** Identifies this game in the application. */
    private final String gameId;
    private final String gameName = "Wordle";

    protected WordleGame(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }
}
