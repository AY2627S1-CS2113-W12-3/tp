package seedu.duke.battleship;

class Cell {
    boolean containsShip;
    boolean isHit;

    Ship occupyingShip;

    Cell() {
        containsShip = false;
        isHit = true;
    }

    void plantShip(Ship ship) {
        this.occupyingShip = ship;
        containsShip = true;
    }
}
