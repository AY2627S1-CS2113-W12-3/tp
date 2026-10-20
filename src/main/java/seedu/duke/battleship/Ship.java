package seedu.duke.battleship;

class Ship {
    private final int size;
    private int hits;

    Ship(int size) {
        this.size = size;
        this.hits = 0;
    }

    boolean isSunk() {
        return hits == size;
    }

    void receiveDamage() {
        hits++;
    }
}
