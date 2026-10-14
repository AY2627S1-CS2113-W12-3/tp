package seedu.duke;

import java.util.List;
import java.util.ArrayList;

import java.util.logging.Logger;

/**
 * Starts the application and coordinates command handling.
 */
public class PlayStationMinusOne {
    /** Records diagnostic events without changing the user-facing output. */
    private static final Logger LOGGER =
            Logger.getLogger(PlayStationMinusOne.class.getName());

    private static final ArrayList<String> GAME_LIST = new ArrayList<String>(List.of("Wordle"));

    /**
     * Main entry point for the PlayStationMinusOne application.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showBanner();
        String input;
        while (true) {
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

            if (command.equals("exit")) {
                LOGGER.info("Exit command received.");
                ui.showGoodbye();
                break;
            } else if (command.equals("help")) {
                ui.showHelp();
            } else if (command.equals("play")) {
                if (arguments.isBlank() || !GAME_LIST.contains(arguments)) {
                    ui.showListOfGames(GAME_LIST);
                } else {
                    ui.showGameStarting(arguments);
                }
            } else if (command.isEmpty()) {
                continue;
            } else {
                LOGGER.warning("Unknown command received: " + command);
                ui.showMessage("Unknown command: " + command);
            }
        }
    }
}
