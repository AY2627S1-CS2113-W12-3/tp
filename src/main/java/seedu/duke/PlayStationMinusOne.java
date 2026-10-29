package seedu.duke;

import java.util.logging.Logger;

/**
 * Starts the application and runs its single input loop.
 *
 * <p>A game never reads input for itself. The loop reads each line and passes it to a
 * {@link CommandHandler}, which routes launcher commands or forwards the line to the
 * active game. This keeps launcher commands reachable at every prompt.
 */
public class PlayStationMinusOne {
    /** Records diagnostic events without changing the user-facing output. */
    private static final Logger LOGGER =
            Logger.getLogger(PlayStationMinusOne.class.getName());

    /**
     * Main entry point for the PlayStationMinusOne application.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showBanner();
        GameList gameList = new GameList();
        CommandHandler commandHandler = new CommandHandler(ui, gameList);

        while (true) {
            String input;
            try {
                input = ui.readLine();
            } catch (IllegalStateException e) {
                LOGGER.info("Input stream ended; closing the application.");
                ui.showGoodbye();
                break;
            }

            if (!commandHandler.shouldContinueAfterInput(input)) {
                break;
            }
        }
    }
}
