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

    /** Checks rows, columns, and diagonals for three matching marks. */
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

    /** Checks whether all nine squares contain a mark. */
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
