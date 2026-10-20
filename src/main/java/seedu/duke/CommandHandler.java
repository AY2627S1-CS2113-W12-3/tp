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
}
