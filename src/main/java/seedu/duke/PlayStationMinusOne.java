package seedu.duke;

import java.util.logging.Logger;

/**
 * Starts the application and coordinates command handling.
 *
 * <p>There is exactly one input loop in the application, and it lives here. A game
 * never reads input for itself; the loop reads every line, decides whether it is a
 * launcher command, and only passes it to the active game if it is not. That is what
 * keeps every command reachable at every prompt -- the player is never shut inside a
 * game and made to leave it before doing anything else.
 */
public class PlayStationMinusOne {
    /** Records diagnostic events without changing the user-facing output. */
    private static final Logger LOGGER =
            Logger.getLogger(PlayStationMinusOne.class.getName());

    private static final String COMMAND_EXIT = "exit";
    private static final String COMMAND_PLAY = "play";
    private static final String COMMAND_QUIT = "quit";
    private static final String QUIT_OUTSIDE_GAME_MESSAGE = "The 'quit' command only works while playing a game.";

    /**
     * Main entry point for the PlayStationMinusOne application.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showBanner();
        GameList gameList = new GameList();
        CommandHandler commandHandler = new CommandHandler(ui, gameList);

        /*
         * The game being played, or null at the main menu. Holding the active game as
         * a variable is the whole point of this design: the alternative, looping
         * inside the game until it finishes, makes the launcher's own commands
         * unreachable for as long as the game runs.
         */
        Game activeGame = null;

        while (true) {
            String input;
            try {
                input = ui.readLine();
            } catch (IllegalStateException e) {
                LOGGER.info("Input stream ended; closing the application.");
                ui.showGoodbye();
                break;
            }

            String[] parsed = Parser.parse(input);
            assert parsed.length == 2 : "Parser must return a command and its arguments";
            String command = parsed[0];
            String arguments = parsed[1];

            // Launcher commands are checked first, so they work whether or not a game
            // is in progress.
            if (command.equals(COMMAND_EXIT)) {
                LOGGER.info("Exit command received.");
                if (activeGame != null) {
                    showExitMessage(ui, activeGame);
                    activeGame.reset();
                }
                ui.showGoodbye();
                break;
            }

            if (commandHandler.handleMenuCommand(command, arguments, input, activeGame)) {
                continue;
            }

            if (command.equals(COMMAND_QUIT) && activeGame == null) {
                LOGGER.warning("Quit command received outside a game.");
                ui.showMessage(QUIT_OUTSIDE_GAME_MESSAGE);
                continue;
            }

            if (command.equals(COMMAND_PLAY)) {
                activeGame = commandHandler.startGame(activeGame, arguments);
                continue;
            }

            // Blank input is ignored rather than forwarded, so pressing Enter during a
            // game does not count as an answer.
            if (command.isEmpty()) {
                continue;
            }

            if (activeGame != null) {
                // Not a launcher command, so it belongs to the game. The raw line is
                // passed rather than the parsed command, because a game's input is not
                // necessarily a command at all -- the calculator receives a number.
                ui.showMessage(activeGame.handleInput(input));
                if (activeGame.isFinished()) {
                    LOGGER.info("Game finished: " + activeGame.getGameName());
                    activeGame.reset();
                    activeGame = null;
                }
                continue;
            }

            LOGGER.warning("Unknown command received: " + command);
            ui.showMessage("Unknown command: " + command);
        }
    }

}
