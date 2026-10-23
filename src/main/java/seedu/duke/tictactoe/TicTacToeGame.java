package seedu.duke.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import seedu.duke.Game;

/** Runs Tic Tac Toe between the player and a random-move computer opponent. */
public class TicTacToeGame implements Game {
    private static final char PLAYER_MARK = 'X';
    private static final char COMPUTER_MARK = 'O';
    private static final String QUIT_COMMAND = "quit";
    private static final String EXIT_MESSAGE = "Returning to the main menu.";

    private final String gameId;
    private final Random random;
    private final TicTacToeBoard board;
    private boolean finished;

    public TicTacToeGame(String gameId) {
        this(gameId, new Random());
    }

    public TicTacToeGame(String gameId, Random random) {
        this.gameId = gameId;
        this.random = random;
        this.board = new TicTacToeBoard();
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
    public String getStartMessage() {
        return "You are X; the computer is O." + System.lineSeparator()
                + "Enter a square from A1 to C3 (for example, B2)." + System.lineSeparator()
                + "The computer picks a random empty square after your move." + System.lineSeparator()
                + "Type 'quit' to leave the game." + System.lineSeparator()
                + board.toDisplayString();
    }

    @Override
    public String getExitMessage() {
        return EXIT_MESSAGE;
    }

    @Override
    public String handleInput(String input) {
        if (finished) {
            return "The game has ended. Start a new game to play again.";
        }

        String coordinate = input == null ? "" : input.trim().toUpperCase(Locale.ROOT);
        if (coordinate.equalsIgnoreCase(QUIT_COMMAND)) {
            finished = true;
            return EXIT_MESSAGE;
        }
        if (!coordinate.matches("[A-C][1-3]")) {
            return "Please enter a valid square from A1 to C3.";
        }

        int row = coordinate.charAt(0) - 'A';
        int column = coordinate.charAt(1) - '1';
        if (!board.placeMark(row, column, PLAYER_MARK)) {
            return "That square is already occupied. Choose an empty square.";
        }

        String playerMove = "You played " + coordinate + " (X).";
        if (board.hasWinner(PLAYER_MARK)) {
            finished = true;
            return formatRound(playerMove, "You win!");
        }
        if (board.isFull()) {
            finished = true;
            return formatRound(playerMove, "It's a draw!");
        }

        int computerCell = chooseRandomEmptyCell();
        int computerRow = computerCell / TicTacToeBoard.SIZE;
        int computerColumn = computerCell % TicTacToeBoard.SIZE;
        boolean movePlaced = board.placeMark(computerRow, computerColumn, COMPUTER_MARK);
        assert movePlaced : "The computer must choose an empty square";

        String moves = playerMove + System.lineSeparator()
                + "Computer played " + formatCoordinate(computerRow, computerColumn) + " (O).";
        if (board.hasWinner(COMPUTER_MARK)) {
            finished = true;
            return formatRound(moves, "The computer wins!");
        }
        if (board.isFull()) {
            finished = true;
            return formatRound(moves, "It's a draw!");
        }
        return formatRound(moves, "");
    }

    private int chooseRandomEmptyCell() {
        List<Integer> emptyCells = new ArrayList<>();
        for (int row = 0; row < TicTacToeBoard.SIZE; row++) {
            for (int column = 0; column < TicTacToeBoard.SIZE; column++) {
                if (board.isCellEmpty(row, column)) {
                    emptyCells.add(row * TicTacToeBoard.SIZE + column);
                }
            }
        }
        return emptyCells.get(random.nextInt(emptyCells.size()));
    }

    private static String formatCoordinate(int row, int column) {
        return String.valueOf((char) ('A' + row)) + (column + 1);
    }

    private String formatRound(String moves, String result) {
        String response = moves + System.lineSeparator()
                + "Board:" + System.lineSeparator()
                + board.toDisplayString();
        return result.isEmpty()
                ? response
                : response + System.lineSeparator() + result + System.lineSeparator() + EXIT_MESSAGE;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void reset() {
        board.reset();
        finished = false;
    }
}
