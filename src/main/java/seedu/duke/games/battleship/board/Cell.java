package seedu.duke.games.battleship.board;

/**
 * Represents a single cell on the Battleship board.
 *
 * <p>Each cell tracks the ship occupying it if it contains a ship
 * and whether it has been attacked.
 */
class Cell {
    Ship occupyingShip;
    boolean hasShip;
    boolean isHit;

    /**
     * Creates an empty cell that has not been attacked.
     */
    Cell() {
        hasShip = false;
        isHit = false;
    }

    /**
     * Places a ship in this cell and marks it as occupied.
     *
     * @param ship the ship to place in this cell
     */

    void plantShip(Ship ship) {
        this.occupyingShip = ship;
        hasShip = true;
    }
}
