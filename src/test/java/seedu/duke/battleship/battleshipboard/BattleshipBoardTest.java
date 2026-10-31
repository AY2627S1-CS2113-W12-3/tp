package seedu.duke.battleship.battleshipboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

/** Tests ship placement, board display, attacks, and invalid coordinates. */
class BattleshipBoardTest {

    @Test
    void newBoardContainsTenShipCells() {
        BattleshipBoard board = new BattleshipBoard(new Random(42));

        assertEquals(10, coordinatesWithSymbol(board, 'S').size());
        assertFalse(board.areAllShipsSunk());
    }

    @Test
    void hiddenBoardConcealsUnhitShips() {
        BattleshipBoard board = new BattleshipBoard(new Random(42));

        assertFalse(board.getFormattedBoard(true).contains("S"));
        assertEquals(49, coordinatesWithSymbol(board, '~', true).size());
    }

    @Test
    void attackingEmptyCellReturnsMissAndMarksCell() {
        BattleshipBoard board = new BattleshipBoard(new Random(42));
        int[] target = coordinatesWithSymbol(board, '~').get(0);

        assertEquals(ShotResult.MISS, board.hitAt(target[0], target[1]));
        assertTrue(board.isCellHit(target[0], target[1]));
        assertEquals('X', symbolAt(board, target[0], target[1], false));
        assertFalse(board.areAllShipsSunk());
    }

    @Test
    void attackingAllShipCellsEventuallySinksAllFourShips() {
        BattleshipBoard board = new BattleshipBoard(new Random(42));
        List<int[]> shipCells = coordinatesWithSymbol(board, 'S');
        int hits = 0;
        int sunk = 0;

        for (int[] target : shipCells) {
            ShotResult result = board.hitAt(target[0], target[1]);
            if (result == ShotResult.HIT) {
                hits++;
            } else if (result == ShotResult.SUNK) {
                sunk++;
            }
            assertTrue(board.isCellHit(target[0], target[1]));
            assertEquals('O', symbolAt(board, target[0], target[1], true));
        }

        assertEquals(6, hits);
        assertEquals(4, sunk);
        assertTrue(board.areAllShipsSunk());
    }

    @Test
    void outOfBoundsAttacksThrowIllegalArgumentException() {
        BattleshipBoard board = new BattleshipBoard(new Random(42));

        assertThrows(IllegalArgumentException.class, () -> board.hitAt(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> board.hitAt(7, 0));
        assertThrows(IllegalArgumentException.class, () -> board.hitAt(0, -1));
        assertThrows(IllegalArgumentException.class, () -> board.hitAt(0, 7));
    }

    /** Returns the coordinates of cells showing the given symbol. */
    private static List<int[]> coordinatesWithSymbol(BattleshipBoard board, char symbol) {
        return coordinatesWithSymbol(board, symbol, false);
    }

    /** Returns coordinates from either the hidden or visible board display. */
    private static List<int[]> coordinatesWithSymbol(BattleshipBoard board, char symbol, boolean isHidden) {
        List<int[]> coordinates = new ArrayList<>();
        String[] lines = board.getFormattedBoard(isHidden).split("\\R");

        for (int row = 0; row < BattleshipBoard.SIDE_LENGTH; row++) {
            String[] cells = lines[row + 1].trim().split("\\s+");
            for (int col = 0; col < BattleshipBoard.SIDE_LENGTH; col++) {
                if (cells[col + 1].charAt(0) == symbol) {
                    coordinates.add(new int[]{row, col});
                }
            }
        }
        return coordinates;
    }

    /** Reads a cell symbol from the board's formatted display. */
    private static char symbolAt(BattleshipBoard board, int row, int col, boolean isHidden) {
        String[] lines = board.getFormattedBoard(isHidden).split("\\R");
        String[] cells = lines[row + 1].trim().split("\\s+");
        return cells[col + 1].charAt(0);
    }
}
