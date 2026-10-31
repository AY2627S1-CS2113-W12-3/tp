package seedu.duke.games.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import seedu.duke.Game;

import seedu.duke.Game;

/**
 * Runs a simple Tic Tac Toe game between the player and a computer opponent.
 *
 * <p>The player always uses X and moves first. After each valid player move,
 * the computer uses O in a randomly chosen empty square.
 */
public class TicTacToeGame implements Game {
    /** The mark used by the player. */
    private static final char PLAYER_MARK = 'X';

    /** The mark used by the computer. */
    private static final char COMPUTER_MARK = 'O';

    /** The command for leaving the current game. */
    private static final String QUIT_COMMAND = "quit";

    /** The message shown when the player leaves the game. */
    private static final String EXIT_MESSAGE = "Returning to the main menu.";

    /** Points awarded when the player wins a game. */
    private static final int POINTS_FOR_WIN = 5;

    /** The ID used to select this game from the main menu. */
    private final String gameId;

    private final String description = "Get three marks in a row to win";

    /** Random source used to choose among empty computer move options. */
    private final Random random;

    /** Stores the current board state. */
    private final TicTacToeBoard board;

    /** Whether the current game has ended. */
    private boolean isFinished;

    /** Points earned by the most recent input, if it won the game. */
    private int pointsEarnedForLastAction;

    /**
     * Creates a game with the given ID and a default random source.
     *
     * @param gameId the ID used to select this game
     */
    public TicTacToeGame(String gameId) {
        this(gameId, new Random());
    }

    /**
     * Creates a game with a caller-supplied random source.
     *
     * <p>Tests can supply a repeatable random source to check computer moves.
     *
     * @param gameId the ID used to select this game
     * @param random the random source used for computer moves
     */
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

    /**
     * Returns the instructions and empty board shown at the start of a game.
     *
     * @return the game instructions and current board
     */
    @Override
    public String getStartMessage() {
        return "You are X; the computer is O." + System.lineSeparator()
                + "Enter a square from A1 to C3 (for example, B2)." + System.lineSeparator()
                + "The computer picks a random empty square after your move." + System.lineSeparator()
                + "Type 'quit' to leave the game." + System.lineSeparator()
                + System.lineSeparator()
                + board.toDisplayString();
    }

    @Override
    public String getExitMessage() {
        return EXIT_MESSAGE;
    }

    /**
     * Processes a coordinate or the quit command.
     *
     * @param input the line entered by the player
     * @return the move result, updated board, or an input message
     */
    @Override
    public String handleInput(String input) {
        pointsEarnedForLastAction = 0;
        if (isFinished) {
            return "The game has ended. Start a new game to play again.";
        }

        String coordinate = input == null ? "" : input.trim().toUpperCase(Locale.ROOT);
        if (coordinate.equalsIgnoreCase(QUIT_COMMAND)) {
            isFinished = true;
            return EXIT_MESSAGE;
        }
        if (!coordinate.matches("[A-C][1-3]")) {
            return "Please enter a valid square from A1 to C3.";
        }

        int row = coordinate.charAt(0) - 'A';
        int column = coordinate.charAt(1) - '1';
        if (!board.tryPlaceMark(row, column, PLAYER_MARK)) {
            return "That square is already occupied. Choose an empty square.";
        }

        String playerMove = "You played " + coordinate + " (X).";
        if (board.hasWinner(PLAYER_MARK)) {
            isFinished = true;
            pointsEarnedForLastAction = POINTS_FOR_WIN;
            return formatRound(playerMove, "You win!");
        }
        if (board.isFull()) {
            isFinished = true;
            return formatRound(playerMove, "It's a draw!");
        }

        int computerCell = chooseRandomEmptyCell();
        int computerRow = computerCell / TicTacToeBoard.SIZE;
        int computerColumn = computerCell % TicTacToeBoard.SIZE;
        boolean isMovePlaced = board.tryPlaceMark(computerRow, computerColumn, COMPUTER_MARK);
        assert isMovePlaced : "The computer must choose an empty square";

        String moves = playerMove + System.lineSeparator()
                + "Computer played " + formatCoordinate(computerRow, computerColumn) + " (O).";
        if (board.hasWinner(COMPUTER_MARK)) {
            isFinished = true;
            return formatRound(moves, "The computer wins!");
        }
        if (board.isFull()) {
            isFinished = true;
            return formatRound(moves, "It's a draw!");
        }
        return formatRound(moves, "");
    }

    /**
     * Chooses a uniformly random empty cell using a list of available indexes.
     *
     * <p>Cells are numbered left to right and then top to bottom. By choosing
     * from only empty cells, the computer cannot overwrite an existing mark.
     *
     * @return the zero-based flattened index of the chosen cell
     */
    private int chooseRandomEmptyCell() {
        List<Integer> emptyCells = new ArrayList<>();
        for (int row = 0; row < TicTacToeBoard.SIZE; row++) {
            for (int column = 0; column < TicTacToeBoard.SIZE; column++) {
                if (board.isCellEmpty(row, column)) {
                    emptyCells.add(row * TicTacToeBoard.SIZE + column);
                }
            }
        }

        assert !emptyCells.isEmpty() : "The computer should move only when an empty cell remains";
        return emptyCells.get(random.nextInt(emptyCells.size()));
    }

    /**
     * Formats a row and column using the same coordinates players enter.
     *
     * @param row zero-based row index
     * @param column zero-based column index
     * @return the coordinate, such as {@code B2}
     */
    private static String formatCoordinate(int row, int column) {
        return String.valueOf((char) ('A' + row)) + (column + 1);
    }

    /**
     * Formats the moves and current board, adding a result when the game ends.
     *
     * @param moves the player and computer moves for this round
     * @param result the win or draw message, or an empty string if play continues
     * @return the round details and current board
     */
    private String formatRound(String moves, String result) {
        String response = moves + System.lineSeparator()
                + "Board:" + System.lineSeparator()
                + board.toDisplayString();
        if (result.isEmpty()) {
            return response;
        }
        return response + System.lineSeparator() + result + System.lineSeparator() + EXIT_MESSAGE;
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public int getPointsEarnedForLastAction() {
        return pointsEarnedForLastAction;
    }

    @Override
    public void reset() {
        board.reset();
        isFinished = false;
        pointsEarnedForLastAction = 0;
    }

    /**
     * Returns a short description of the Tic Tac Toe game
     *
     * @return description the description
     */
    @Override
    public String getDescription() {
        return description;
    }
}
