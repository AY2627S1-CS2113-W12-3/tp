package seedu.duke.games.battleship.board;

/**
 * Represents a ship on the Battleship board.
 *
 * <p>Each ship has a fixed size and tracks the number of times
 * it has been hit. A ship is sunk when its hits equal its size.
 */
class Ship {
    /** Number of cells occupied by the ship. */
    private final int size;

    /** Number of hits the ship has received. */
    private int hits;

    /**
     * Creates a ship with the specified size and no initial damage.
     *
     * @param size the number of cells occupied by the ship
     */
    Ship(int size) {
        this.size = size;
        this.hits = 0;
    }

    /**
     * Checks whether the ship has been sunk.
     *
     * @return true if the number of hits equals the ship's size,
     *         false otherwise
     */
    boolean isSunk() {
        return hits == size;
    }

    /**
     * Records a hit on the ship by increasing its damage count.
     */
    void receiveDamage() {
        if (isSunk()) {
            throw new IllegalStateException("Cannot damage a ship that has already sunk.");
        }

        hits++;
    }
}
