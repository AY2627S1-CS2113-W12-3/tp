package seedu.duke;

import java.util.logging.Logger;

import seedu.duke.wordle.WordleException;
import seedu.duke.wordle.WordleGame;

/**
 * Receives application input and coordinates command handling.
 */
public class CommandHandler {
    /** Records command events without changing the user-facing output. */
    private static final Logger LOGGER = Logger.getLogger(CommandHandler.class.getName());

    private static final String QUIT_OUTSIDE_GAME_MESSAGE =
            "The 'quit' command only works while playing a game.";

    private static final String COMMAND_HELP = "help";
    private static final String COMMAND_LIST = "list";
    private static final String LIST_ARGUMENT_LETTERS = "letters";

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

    /**
     * Handles menu information commands when they are present.
     *
     * @param command the parsed command
     * @param arguments the parsed command arguments
     * @param input the full input line
     * @param activeGame the current game, or {@code null} at the menu
     * @return whether this handler processed the command
     */
    boolean handleMenuCommand(String command, String arguments, String input, Game activeGame) {
        if (command.equals(COMMAND_HELP)) {
            LOGGER.info("Help command received.");
            ui.showHelp(activeGame instanceof WordleGame);
            return true;
        }

        if (!command.equals(COMMAND_LIST)) {
            return false;
        }

        if (arguments.equalsIgnoreCase(LIST_ARGUMENT_LETTERS)) {
            if (activeGame instanceof WordleGame) {
                ui.showMessage(activeGame.handleInput(input));
            } else {
                ui.showMessage(WordleException.listLettersOnlyInWordle());
            }
            return true;
        }

        LOGGER.info("List command received.");
        ui.showListOfGames(gameList);
        return true;
    }

    /**
     * Starts a selected game, leaving the current one first when switching.
     *
     * @param activeGame the current game, or {@code null} at the menu
     * @param gameId the game ID entered by the player
     * @return the selected game, or the current game if the ID is invalid
     */
    Game startGame(Game activeGame, String gameId) {
        Game requested = gameList.findGameById(gameId);
        if (requested == null) {
            LOGGER.warning("Invalid game ID: " + gameId);
            ui.showMessage("Please enter a valid game ID.");
            ui.showListOfGames(gameList);
            return activeGame;
        }

        if (activeGame != null) {
            // Show the outgoing game's summary before resetting it.
            showExitMessage(activeGame);
            activeGame.reset();
        }

        LOGGER.info("Starting game: " + gameId);
        ui.showGameStarting(requested.getGameName());
        String startMessage = requested.getStartMessage();
        if (!startMessage.isEmpty()) {
            ui.showMessage(startMessage);
        }
        return requested;
    }

    /**
     * Sends a full input line to the current game and clears it when it finishes.
     *
     * @param activeGame the game receiving input
     * @param input the full line entered by the player
     * @return the game if it is still active, or {@code null} if it finished
     */
    Game handleGameInput(Game activeGame, String input) {
        ui.showMessage(activeGame.handleInput(input));
        if (activeGame.isFinished()) {
            LOGGER.info("Game finished: " + activeGame.getGameName());
            activeGame.reset();
            return null;
        }
        return activeGame;
    }

    /** Shows the final game message and goodbye output before the application exits. */
    void handleExit(Game activeGame) {
        LOGGER.info("Exit command received.");
        if (activeGame != null) {
            showExitMessage(activeGame);
            activeGame.reset();
        }
        ui.showGoodbye();
    }

    /** Displays the response for {@code quit} when no game is active. */
    void handleQuitOutsideGame() {
        LOGGER.warning("Quit command received outside a game.");
        ui.showMessage(QUIT_OUTSIDE_GAME_MESSAGE);
    }

    /** Displays a game's closing message, if it has one. */
    private void showExitMessage(Game game) {
        String exitMessage = game.getExitMessage();
        if (!exitMessage.isEmpty()) {
            ui.showMessage(exitMessage);
        }
    }
}
