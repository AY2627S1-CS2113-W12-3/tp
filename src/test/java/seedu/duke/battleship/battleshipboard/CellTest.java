package seedu.duke.battleship.battleshipboard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Tests the initial state and ship occupancy of a board cell. */
class CellTest {

    @Test
    void newCellIsEmptyAndUnhit() {
        Cell cell = new Cell();

        assertFalse(cell.hasShip);
        assertFalse(cell.isHit);
    }

    @Test
    void plantingShipMarksCellAsOccupied() {
        Cell cell = new Cell();
        Ship ship = new Ship(2);

        cell.plantShip(ship);

        assertTrue(cell.hasShip);
        assertSame(ship, cell.occupyingShip);
        assertFalse(cell.isHit);
    }
}
