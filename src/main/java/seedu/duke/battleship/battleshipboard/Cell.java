package seedu.duke.battleship.battleshipboard;

class Cell {
    Ship occupyingShip;
    boolean containsShip;
    boolean isHit;

    Cell() {
        containsShip = false;
        isHit = false;
    }

    void plantShip(Ship ship) {
        this.occupyingShip = ship;
        containsShip = true;
    }
}
