package seedu.duke.battleship;

class Cell {
    Ship occupyingShip;
    boolean containsShip;
    boolean isHit;

    Cell() {
        containsShip = false;
        isHit = true;
    }

    void plantShip(Ship ship) {
        this.occupyingShip = ship;
        containsShip = true;
    }
}
