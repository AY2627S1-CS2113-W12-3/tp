package seedu.duke.tictactoe;

import seedu.duke.Game;

/** A placeholder for the Tic Tac Toe game while its board and rules are added. */
public class TicTacToeGame implements Game {
    private final String gameId;

    /** Creates a game with the ID used by the launcher. */
    public TicTacToeGame(String gameId) {
        this.gameId = gameId;
    }

    @Override
    public String getGameId() {
        return gameId;
    }

    @Override
    public String getGameName() {
        return "Tic Tac Toe";
    }

    @Override
    public String handleInput(String input) {
        return "Tic Tac Toe is not ready yet.";
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void reset() {
        // The game has no state until the board is added.
    }
}
