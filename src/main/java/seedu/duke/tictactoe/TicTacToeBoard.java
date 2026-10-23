package seedu.duke.tictactoe;

import java.util.Arrays;

/** Stores the marks on a 3-by-3 Tic Tac Toe board. */
public class TicTacToeBoard {
    static final int SIZE = 3;
    private static final char EMPTY = '.';
    private final char[][] cells = new char[SIZE][SIZE];

    /** Creates a board with all squares empty. */
    public TicTacToeBoard() {
        reset();
    }

    /** Places an X or O in an empty square. */
    public boolean placeMark(int row, int column, char mark) {
        if (!isInBounds(row, column) || cells[row][column] != EMPTY || (mark != 'X' && mark != 'O')) {
            return false;
        }
        cells[row][column] = mark;
        return true;
    }

    /** Checks whether an on-board square is empty. */
    public boolean isCellEmpty(int row, int column) {
        return isInBounds(row, column) && cells[row][column] == EMPTY;
    }

    /** Clears all marks from the board. */
    public void reset() {
        for (char[] row : cells) {
            Arrays.fill(row, EMPTY);
        }
    }

    /** Returns a printable board with row letters and column numbers. */
    public String toDisplayString() {
        StringBuilder display = new StringBuilder("  1 2 3");
        for (int row = 0; row < SIZE; row++) {
            display.append(System.lineSeparator()).append((char) ('A' + row));
            for (int column = 0; column < SIZE; column++) {
                display.append(' ').append(cells[row][column]);
            }
        }
        return display.toString();
    }

    private static boolean isInBounds(int row, int column) {
        return row >= 0 && row < SIZE && column >= 0 && column < SIZE;
    }
}
