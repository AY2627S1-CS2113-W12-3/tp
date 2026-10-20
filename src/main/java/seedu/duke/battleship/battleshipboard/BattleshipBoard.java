package seedu.duke.battleship.battleshipboard;

import java.util.Random;

public class BattleshipBoard {
    public static final int SIDE_LENGTH = 7;
    public static final int[] SHIP_SIZES = {4, 3, 2, 1};

    private int shipsRemaining;
    private Cell[][] board = new Cell[SIDE_LENGTH][SIDE_LENGTH];


    public BattleshipBoard(Random random) {
        for (int row = 0; row < SIDE_LENGTH; row++) {
            for (int col = 0; col < SIDE_LENGTH; col++) {
                board[row][col] = new Cell();
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

                    board[r][c].plantShip(ship);
                }

                shipsRemaining++;
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

            if (board[r][c].containsShip) {
                return false;
            }
        }

        return true;
    }

    public String stringFormattedBoard(boolean isHidden) {
        StringBuilder board = new StringBuilder("   1 2 3 4 5 6 7"
                + System.lineSeparator());

        for (int row = 0; row < SIDE_LENGTH; row++) {
            board.append((char) ('A' + row)).append("  ");

            for (int col = 0; col < SIDE_LENGTH; col++) {
                char icon;
                Cell cell = this.board[row][col];

                if (cell.isHit) {
                    icon = cell.containsShip ? 'O' : 'X';
                } else if (cell.containsShip && !isHidden) {
                    icon = 'S';
                } else {
                    icon = '~';
                }

                board.append(icon);
                board.append(' ');
            }

            board.append(System.lineSeparator());
        }

        return board.toString();
    }


    public ShotResult hitAt(int row, int col) {
        Cell cell = board[row][col];
        assert(!cell.isHit);

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

    public boolean isCellHit(int row, int col) {
        return board[row][col].isHit;
    }

    public boolean allShipsSunk() {
        return shipsRemaining == 0;
    }
}
