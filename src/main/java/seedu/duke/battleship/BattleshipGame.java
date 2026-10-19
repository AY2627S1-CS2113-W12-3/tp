package seedu.duke.battleship;

import seedu.duke.Game;

import java.util.logging.Logger;

public class BattleshipGame implements Game {
    private static final Logger LOGGER = Logger.getLogger(BattleshipGame.class.getName());

    private final String gameId;
    private final String gameName = "Battleship";
    private boolean isGameOver = false;

    private static final String QUIT_COMMAND = "quit";
    private final String EXIT_GAME_MESSAGE = "Returning to the main menu.";


    public BattleshipGame(String gameId) {
        this.gameId = gameId;
    }

    @Override
    public String getGameId() {
        return gameId;
    }

    @Override
    public String getGameName() {
        return gameName;
    }

    @Override
    public String getStartMessage() {
        return "Sink all 3 enemy ships before the computer sinks yours!" + System.lineSeparator()
                + "Ships: lengths 4, 3, and 2. Ships are placed automatically." + System.lineSeparator()
                + "Fire by typing a coordinate from A1 to H8 (for example, B4)." + System.lineSeparator()
                + "Type 'board' to see both boards." + System.lineSeparator()
                + "Type 'quit' to leave." + System.lineSeparator()
                + "Legend: S = your ship, X = hit, O = miss, ~ = unknown";
    }


    @Override
    public String getExitMessage() {
        return Game.super.getExitMessage();
    }

    @Override
    public String handleInput(String input) {
        return "";
    }

    @Override
    public boolean isFinished() {
        return isGameOver;
    }

    @Override
    public void reset() {

    }
}
