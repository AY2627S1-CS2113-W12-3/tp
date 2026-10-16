package seedu.duke;

import java.util.logging.Logger;

/**
 * Starts the application and coordinates command handling.
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
                LOGGER.info("Help command received.");
                ui.showHelp();
            } else if (command.equals("play")) {
                if (!arguments.matches("\\d+")) {
                    LOGGER.warning("Invalid game ID format: " + arguments);
                    ui.showMessage("Please enter a valid game ID.");
                    ui.showListOfGames(gameList);
                    continue;
                }

                Game selectedGame = null;
                for (Game game : gameList.getGames()) {
                    if (game.getGameId().equals(arguments)) {
                        selectedGame = game;
                        break;
                    }
                }

                if (selectedGame == null) {
                    LOGGER.warning("No game selected or invalid game: " + arguments);
                    ui.showListOfGames(gameList);
                } else {
                    assert selectedGame.getGameId().equals(arguments);
                    LOGGER.info("Starting game: " + arguments);
                    ui.showGameStarting(selectedGame.getGameName());
                    // Let the game speak before the first input is read, if it has
                    // anything to say. Games with no opening text return "".
                    String startMessage = selectedGame.getStartMessage();
                    if (!startMessage.isEmpty()) {
                        ui.showMessage(startMessage);
                    }
                    // Play the game until it is finished.
                    while (!selectedGame.isFinished()) {
                        ui.showMessage(selectedGame.handleInput(ui.readLine()));
                    }

                    selectedGame.reset();
                    assert(selectedGame.isFinished() == false);
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
