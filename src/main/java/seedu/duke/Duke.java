package seedu.duke;

import java.util.logging.Logger;

/**
 * Starts the application and coordinates command handling.
 */
public class Duke {
    /** Records diagnostic events without changing the user-facing output. */
    private static final Logger LOGGER = Logger.getLogger(Duke.class.getName());

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showBanner();
        Game activeGame = null;
        String input;
        while (true) {
            try {
                input = ui.readLine();
            } catch (IllegalStateException e) {
                LOGGER.info("Input stream ended; closing the application.");
                ui.showGoodbye();
                break;
            }

            if (activeGame != null) {
                if (input.equalsIgnoreCase("exitgame")) {
                    LOGGER.info("Player left the active game.");
                    ui.showMessage("Game ended. Returning to the launcher.");
                    activeGame = null;
                } else {
                    ui.showMessage(activeGame.handleInput(input));
                    if (activeGame.isFinished()) {
                        activeGame = null;
                    }
                }
                continue;
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
            } else if (command.equals("play") && arguments.equalsIgnoreCase("wordle")) {
                activeGame = new WordleGame();
                LOGGER.info("Started a Wordle game.");
                ui.showMessage("Wordle started. Guess a five-letter word.");
                ui.showMessage("You have 5 attempts. Type exitgame to return to the launcher.");
            } else if (command.isEmpty()) {
                continue;
            } else {
                LOGGER.warning("Unknown command received: " + command);
                ui.showMessage("Unknown command: " + command);
            }
        }
    }
}
