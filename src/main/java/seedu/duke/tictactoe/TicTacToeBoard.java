package seedu.duke.tictactoe;

import java.util.Arrays;

/**
 * Stores and displays the marks on a 3-by-3 Tic Tac Toe board.
 *
 * <p>The board knows the rules for placing marks and checking outcomes, while
 * {@link TicTacToeGame} handles turns and player input.
 */
public class TicTacToeBoard {
    /** Number of rows and columns in the board. */
    static final int SIZE = 3;

    /** The value used to show an empty square. */
    private static final char EMPTY = '.';

    /** The board cells, addressed by zero-based row and column. */
    private final char[][] cells = new char[SIZE][SIZE];

    /** Creates a board with all squares empty. */
    public TicTacToeBoard() {
        reset();
    }

    /**
     * Places a mark in a square if it is on the board and currently empty.
     *
     * @param row zero-based row index
     * @param column zero-based column index
     * @param mark the mark to place, either {@code X} or {@code O}
     * @return true if the mark was placed, otherwise false
     */
    public boolean tryPlaceMark(int row, int column, char mark) {
        if (!isInBounds(row, column) || cells[row][column] != EMPTY || (mark != 'X' && mark != 'O')) {
            return false;
        }
        cells[row][column] = mark;
        return true;
    }

    /**
     * Checks whether a square is empty.
     *
     * @param row zero-based row index
     * @param column zero-based column index
     * @return true if the square is on the board and empty
     */
    public boolean isCellEmpty(int row, int column) {
        return isInBounds(row, column) && cells[row][column] == EMPTY;
    }

    /**
     * Checks whether the given mark fills any complete row, column, or diagonal.
     *
     * @param mark the mark to check
     * @return true if the mark has three in a row
     */
    public boolean hasWinner(char mark) {
        for (int index = 0; index < SIZE; index++) {
            boolean rowMatches = cells[index][0] == mark
                    && cells[index][1] == mark
                    && cells[index][2] == mark;
            boolean columnMatches = cells[0][index] == mark
                    && cells[1][index] == mark
                    && cells[2][index] == mark;
            if (rowMatches || columnMatches) {
                return true;
            }
        }

        boolean diagonalMatches = cells[0][0] == mark
                && cells[1][1] == mark
                && cells[2][2] == mark;
        boolean otherDiagonalMatches = cells[0][2] == mark
                && cells[1][1] == mark
                && cells[2][0] == mark;
        return diagonalMatches || otherDiagonalMatches;
    }

    /**
     * Checks whether every square contains a mark.
     *
     * @return true if there are no empty squares
     */
    public boolean isFull() {
        for (char[] row : cells) {
            for (char cell : row) {
                if (cell == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Clears all marks from the board. */
    public void reset() {
        for (char[] row : cells) {
            Arrays.fill(row, EMPTY);
        }
    }

    /**
     * Returns the board with row letters and column numbers for move entry.
     *
     * @return a printable board, for example {@code A . X .}
     */
    public String toDisplayString() {
        StringBuilder display = new StringBuilder("  1 2 3");
        for (int row = 0; row < SIZE; row++) {
            display.append(System.lineSeparator())
                    .append((char) ('A' + row));
            for (int column = 0; column < SIZE; column++) {
                display.append(' ').append(cells[row][column]);
            }
        }
        return display.toString();
    }

    /**
     * Checks whether a row and column refer to a square on the board.
     *
     * @param row zero-based row index
     * @param column zero-based column index
     * @return true if both indexes are within the board
     */
    private static boolean isInBounds(int row, int column) {
        return row >= 0 && row < SIZE && column >= 0 && column < SIZE;
    }
}
