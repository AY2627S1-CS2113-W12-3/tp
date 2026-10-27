package seedu.duke;

import java.io.IOException;
import java.util.logging.Logger;

import seedu.duke.wordle.WordleException;
import seedu.duke.wordle.WordleGame;

/**
 * Decides what to do with each line of player input.
 *
 * <p>The launcher passes input here after reading it. This class handles menu
 * commands, keeps track of the active game, and forwards other input to that game.
 */
public class CommandHandler {
    /** Records diagnostic events without changing the user-facing output. */
    private static final Logger LOGGER = Logger.getLogger(CommandHandler.class.getName());

    private static final String COMMAND_EXIT = "exit";
    private static final String COMMAND_HELP = "help";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_PLAY = "play";
    private static final String COMMAND_QUIT = "quit";
    private static final String COMMAND_SCORE = "score";
    private static final String LIST_ARGUMENT_LETTERS = "letters";
    private static final String QUIT_OUTSIDE_GAME_MESSAGE =
            "The 'quit' command only works while playing a game.";
    private static final String LOAD_POINTS_ERROR_MESSAGE = "Unable to load your saved points.";
    private static final String SAVE_POINTS_ERROR_MESSAGE = "Unable to save your points.";

    /** Handles console output for command responses. */
    private final Ui ui;
    /** Provides the games that can be started. */
    private final GameList gameList;
    /** Reads and updates the player's persistent total. */
    private final PointsStore pointsStore;
    /** The game currently receiving player input, or {@code null} at the menu. */
    private Game activeGame;

    /**
     * Creates a command handler using the given UI and available games.
     *
     * @param ui the UI used to display responses
     * @param gameList the games that can be started
     */
    public CommandHandler(Ui ui, GameList gameList) {
        this(ui, gameList, new PointsStore());
    }

    /**
     * Creates a command handler with an injected points store.
     *
     * <p>Tests can pass a temporary store to avoid changing the real player profile.
     *
     * @param ui the UI used to display responses
     * @param gameList the games that can be started
     * @param pointsStore the store used for persistent points
     */
    public CommandHandler(Ui ui, GameList gameList, PointsStore pointsStore) {
        this.ui = ui;
        this.gameList = gameList;
        this.pointsStore = pointsStore;
    }

    /**
     * Parses and handles one line of input.
     *
     * @param input the line entered by the player
     * @return {@code true} while the application should continue, or {@code false} after {@code exit}
     */
    public boolean processInput(String input) {
        String[] parsed = Parser.parse(input);
        assert parsed.length == 2 : "Parser must return a command and its arguments";
        String command = parsed[0];
        String arguments = parsed[1];

        // Check menu commands first so they stay available while a game is running.
        if (command.equals(COMMAND_EXIT)) {
            LOGGER.info("Exit command received.");
            if (activeGame != null) {
                showExitMessage(activeGame);
                activeGame.reset();
            }
            ui.showGoodbye();
            return false;
        }

        if (command.equals(COMMAND_HELP)) {
            LOGGER.info("Help command received.");
            if (activeGame == null) {
                ui.showHelp();
            } else {
                ui.showHelp(activeGame.getCommands());
            }

            return true;
        }

        if (command.equals(COMMAND_SCORE)) {
            showPoints();
            return true;
        }

        if (command.equals(COMMAND_LIST)) {
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

        if (command.equals(COMMAND_QUIT) && activeGame == null) {
            LOGGER.warning("Quit command received outside a game.");
            ui.showMessage(QUIT_OUTSIDE_GAME_MESSAGE);
            return true;
        }

        if (command.equals(COMMAND_PLAY)) {
            activeGame = startGame(arguments);
            return true;
        }

        // A blank line is ignored instead of being sent to the active game.
        if (command.isEmpty()) {
            return true;
        }

        if (activeGame != null) {
            // Game input can be a guess or a number, so pass the full line unchanged.
            ui.showMessage(activeGame.handleInput(input));
            if (activeGame.isFinished()) {
                LOGGER.info("Game finished: " + activeGame.getGameName());
                activeGame.reset();
                activeGame = null;
            }
            return true;
        }

        LOGGER.warning("Unknown command received: " + command);
        ui.showMessage("Unknown command: " + command);
        return true;
    }

    /** Displays the saved total without changing it. */
    private void showPoints() {
        try {
            ui.showMessage("Total points: " + pointsStore.getTotalPoints());
        } catch (IOException e) {
            LOGGER.warning("Unable to load points: " + e.getMessage());
            ui.showMessage(LOAD_POINTS_ERROR_MESSAGE);
        }
    }

    /**
     * Starts a game, leaving the current one first if the requested ID is valid.
     *
     * @param gameId the ID the player entered
     * @return the requested game, or the current game if the ID is invalid
     */
    private Game startGame(String gameId) {
        Game requested = gameList.findGameById(gameId);
        if (requested == null) {
            LOGGER.warning("Invalid game ID: " + gameId);
            ui.showMessage("Please enter a valid game ID.");
            ui.showListOfGames(gameList);
            return activeGame;
        }

        // Show any session summary before switching away from the current game.
        if (activeGame != null) {
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

    /** Displays a game's closing message, if it has one. */
    private void showExitMessage(Game game) {
        String exitMessage = game.getExitMessage();
        if (!exitMessage.isEmpty()) {
            ui.showMessage(exitMessage);
        }
    }
}
