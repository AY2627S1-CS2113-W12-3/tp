package seedu.duke.battleship;

import java.util.Random;

class BattleshipBoard {
    private final int SIDE_LENGTH = 10;
    private final int[] SHIP_SIZES = {5, 4, 3, 2, 1};

    private int shipsRemaining;

    private final Cell[][] BOARD = new Cell[SIDE_LENGTH][SIDE_LENGTH];

    BattleshipBoard(Random random) {
        for (int row = 0; row < SIDE_LENGTH; row++) {
            for (int col = 0; col < SIDE_LENGTH; col++) {
                BOARD[row][col] = new Cell();
            }
        }

        for (int size : SHIP_SIZES) {
            placeShip(random, new Ship(size), size);
        }
    }

    private void placeShip(Random random, Ship ship, int size) {
        while (true) {
            int row = random.nextInt(SIDE_LENGTH);
            int col = random.nextInt(SIDE_LENGTH);
            boolean isHorizontal = random.nextBoolean();

            if (canPlaceShip(row, col, size, isHorizontal)) {
                for (int i = 0; i < size; i++) {
                    int r = row + (isHorizontal ? 0 : i);
                    int c = col + (isHorizontal ? i : 0);

                    BOARD[r][c].plantShip(ship);
                    shipsRemaining++;
                }

                return;
            }
        }
    }

    private boolean canPlaceShip(int row, int col, int length, boolean isHorizontal) {
        if (isHorizontal && col + length > SIDE_LENGTH) {
            return false;
        }

        if (!isHorizontal && row + length > SIDE_LENGTH) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            int r = row + (isHorizontal ? 0 : i);
            int c = col + (isHorizontal ? i : 0);

            if (BOARD[r][c].containsShip) {
                return false;
            }
        }

        return true;
    }

    String stringFormattedBoard(boolean isHidden) {
        StringBuilder board = new StringBuilder("   1 2 3 4 5 6 7 8"
                + System.lineSeparator());

        for (int row = 0; row < SIDE_LENGTH; row++) {
            board.append((char) ('A' + row)).append("  ");

            for (int col = 0; col < SIDE_LENGTH; col++) {
                char icon;
                Cell cell = BOARD[row][col];

                if (cell.isHit) {
                    icon = cell.containsShip ? 'O' : 'X';
                } else if (cell.containsShip && !isHidden) {
                    icon = 'S';
                } else {
                    icon = '~';
                }

                board.append(icon);
            }

            board.append(System.lineSeparator());
        }

        return board.toString();
    }

    ShotResult hitAt(int row, int col) {
        Cell cell = BOARD[row][col];

        if (cell.isHit) {
            throw new IllegalArgumentException("Coordinate already fired upon");
        }

        cell.isHit = true;

        if (cell.containsShip) {
            Ship ship = cell.occupyingShip;
            ship.receiveDamage();

            if (ship.isSunk()) {
                shipsRemaining--;
                return ShotResult.SUNK;
            }

            return ShotResult.HIT;
        }

        return ShotResult.MISS;
    }

    int getShipsRemaining() {
        return shipsRemaining;
    }

    boolean allShipsSunk() {
        return shipsRemaining == 0;
    }
}
