package seedu.duke;

/**
 * Receives application input and coordinates command handling.
 */
public class CommandHandler {
    /** Handles output for commands. */
    private final Ui ui;
    /** Provides the games that can be started. */
    private final GameList gameList;

    /**
     * Creates a command handler with the UI and game list it needs.
     *
     * @param ui the UI used to display responses
     * @param gameList the available games
     */
    public CommandHandler(Ui ui, GameList gameList) {
        this.ui = ui;
        this.gameList = gameList;
    }
}
