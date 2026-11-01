package seedu.duke.games.battleship.board;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Tests ship damage and sinking. */
class ShipTest {

    @Test
    void newShipIsNotSunk() {
        Ship ship = new Ship(2);

        assertFalse(ship.isSunk());
    }

    @Test
    void shipSinksOnlyAfterAllSegmentsAreHit() {
        Ship ship = new Ship(2);

        ship.receiveDamage();
        assertFalse(ship.isSunk());

        ship.receiveDamage();
        assertTrue(ship.isSunk());
    }

    @Test
    void sunkShipCannotReceiveFurtherDamage() {
        Ship ship = new Ship(1);
        ship.receiveDamage();

        assertThrows(IllegalStateException.class, ship::receiveDamage);
    }
}
