package seedu.duke.tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Tests square placement, board display, and Tic Tac Toe outcomes. */
class TicTacToeBoardTest {

    @Test
    void newBoardDisplaysNineEmptySquares() {
        TicTacToeBoard board = new TicTacToeBoard();
        String lineBreak = System.lineSeparator();

        assertEquals("  1 2 3" + lineBreak
                + "A . . ." + lineBreak
                + "B . . ." + lineBreak
                + "C . . .", board.toDisplayString());
    }

    @Test
    void onlyAnEmptySquareAndValidMarkCanBeUsed() {
        TicTacToeBoard board = new TicTacToeBoard();

        assertTrue(board.placeMark(0, 0, 'X'));
        assertFalse(board.placeMark(0, 0, 'O'));
        assertFalse(board.placeMark(-1, 0, 'O'));
        assertFalse(board.placeMark(0, 3, 'O'));
        assertFalse(board.placeMark(1, 1, 'Y'));
        assertFalse(board.isCellEmpty(0, 0));
        assertFalse(board.isCellEmpty(3, 0));
        assertTrue(board.isCellEmpty(1, 1));
    }

    @Test
    void detectsRowAndColumnWins() {
        TicTacToeBoard rowBoard = new TicTacToeBoard();
        rowBoard.placeMark(0, 0, 'X');
        rowBoard.placeMark(0, 1, 'X');
        rowBoard.placeMark(0, 2, 'X');

        TicTacToeBoard columnBoard = new TicTacToeBoard();
        columnBoard.placeMark(0, 1, 'O');
        columnBoard.placeMark(1, 1, 'O');
        columnBoard.placeMark(2, 1, 'O');

        assertTrue(rowBoard.hasWinner('X'));
        assertTrue(columnBoard.hasWinner('O'));
    }

    @Test
    void detectsBothDiagonalWins() {
        TicTacToeBoard mainDiagonal = new TicTacToeBoard();
        mainDiagonal.placeMark(0, 0, 'X');
        mainDiagonal.placeMark(1, 1, 'X');
        mainDiagonal.placeMark(2, 2, 'X');

        TicTacToeBoard otherDiagonal = new TicTacToeBoard();
        otherDiagonal.placeMark(0, 2, 'O');
        otherDiagonal.placeMark(1, 1, 'O');
        otherDiagonal.placeMark(2, 0, 'O');

        assertTrue(mainDiagonal.hasWinner('X'));
        assertTrue(otherDiagonal.hasWinner('O'));
    }

    @Test
    void fullBoardWithoutWinnerIsDrawAndCanBeReset() {
        TicTacToeBoard board = new TicTacToeBoard();
        char[][] draw = {
            {'X', 'O', 'X'},
            {'X', 'O', 'O'},
            {'O', 'X', 'X'}
        };

        for (int row = 0; row < draw.length; row++) {
            for (int column = 0; column < draw[row].length; column++) {
                assertTrue(board.placeMark(row, column, draw[row][column]));
            }
        }

        assertTrue(board.isFull());
        assertFalse(board.hasWinner('X'));
        assertFalse(board.hasWinner('O'));

        board.reset();

        assertFalse(board.isFull());
        assertTrue(board.isCellEmpty(0, 0));
        assertTrue(board.toDisplayString().contains("A . . ."));
    }
}
