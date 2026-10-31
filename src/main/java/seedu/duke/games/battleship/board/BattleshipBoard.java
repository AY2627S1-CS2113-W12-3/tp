package seedu.duke.games.battleship.board;

import java.util.Random;

/**
 * Represents a Battleship board containing randomly placed ships.
 *
 * <p>The board consists of a 7x7 grid of cells containing four ships
 * of different sizes. It manages ship placement, processes attacks,
 * and tracks whether all ships have been sunk.
 */
public class BattleshipBoard {
    public static final int SIDE_LENGTH = 7;
    static final int[] SHIP_SIZES = {4, 3, 2, 1};

    private int shipsRemaining;
    private final Cell[][] board = new Cell[SIDE_LENGTH][SIDE_LENGTH];


    /**
     * Creates a Battleship board with randomly placed ships.
     *
     * <p>Initialises every cell before placing the ships
     * horizontally or vertically without overlapping.
     *
     * @param random the random number generator used for ship placement
     */
    public BattleshipBoard(Random random) {
        for (int row = 0; row < SIDE_LENGTH; row++) {
            for (int col = 0; col < SIDE_LENGTH; col++) {
                board[row][col] = new Cell();
            }
        }

        for (int size : SHIP_SIZES) {
            placeShip(random, new Ship(size), size);
        }

        assert (shipsRemaining == SHIP_SIZES.length) : "All ships must be placed on the board";
    }

    /**
     * Places a ship at a randomly selected valid position.
     *
     * <p>Randomly selects a starting coordinate and orientation
     * until a position is found where the ship fits without
     * overlapping another ship.
     *
     * @param random the random number generator used for placement
     * @param ship the ship to place on the board
     * @param size the number of cells occupied by the ship
     */
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

    /**
     * Checks whether a ship can be placed at the specified position.
     *
     * <p>A placement is valid if the ship remains within the board
     * boundaries and does not overlap any existing ships.
     *
     * @param row the zero-based starting row
     * @param col the zero-based starting column
     * @param length the number of cells occupied by the ship
     * @param isHorizontal true for horizontal placement, false for vertical
     * @return true if the ship can be placed, false otherwise
     */
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

            if (board[r][c].hasShip) {
                return false;
            }
        }

        return true;
    }

    /**
     * Generates a formatted text representation of the board.
     *
     * <p>Displays row and column labels together with symbols
     * representing ships, hits, misses, and unknown cells.
     * Unhit ships are hidden when {@code isHidden} is true.
     *
     * @param isHidden whether to hide ships that have not been hit
     * @return the formatted board as a string
     */
    public String getFormattedBoard(boolean isHidden) {
        StringBuilder boardDisplay = new StringBuilder("   1 2 3 4 5 6 7" + System.lineSeparator());

        for (int row = 0; row < SIDE_LENGTH; row++) {
            boardDisplay.append((char) ('A' + row)).append("  ");

            for (int col = 0; col < SIDE_LENGTH; col++) {
                Cell cell = this.board[row][col];

                boardDisplay.append(getCellSymbol(cell, isHidden));
                boardDisplay.append(' ');
            }

            boardDisplay.append(System.lineSeparator());
        }

        return boardDisplay.toString();
    }

    /**
     * Returns the symbol representing a cell's status.
     *
     * <p>Displays hits, misses, visible ships, or unknown cells.
     * Unhit ships are hidden when isHidden is true.
     *
     * @param cell the cell whose status is being displayed
     * @param isHidden whether to hide ships that have not been hit
     * @return the character representing the cell's status
     */
    private static char getCellSymbol(Cell cell, boolean isHidden) {
        if (cell.isHit) {
            return cell.hasShip ? 'O' : 'X';
        }

        if (cell.hasShip && !isHidden) {
            return 'S';
        }

        return '~';
    }

    /**
     * Processes an attack at the specified board coordinate.
     *
     * <p>Marks the targeted cell as hit. If the cell contains
     * a ship, the ship receives damage. When the final segment
     * of a ship is hit, the remaining ship count decreases.
     *
     * <p>The targeted cell must not have been attacked previously.
     *
     * @param row the zero-based row of the targeted cell
     * @param col the zero-based column of the targeted cell
     * @return MISS if the cell is empty, HIT if a ship is damaged,
     *         or SUNK if the attack sinks a ship
     */
    public ShotResult hitAt(int row, int col) {
        if (row < 0 || row >= SIDE_LENGTH || col < 0 || col >= SIDE_LENGTH) {
            throw new IllegalArgumentException("Attack coordinates are outside the board.");
        }

        Cell cell = board[row][col];
        assert(!cell.isHit);

        cell.isHit = true;
        if (cell.hasShip) {
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

    /**
     * Checks whether a cell has already been attacked.
     *
     * @param row the zero-based row of the cell
     * @param col the zero-based column of the cell
     * @return true if the cell has been attacked, false otherwise
     */
    public boolean isCellHit(int row, int col) {
        return board[row][col].isHit;
    }

    /**
     * Checks whether every ship on the board has been sunk.
     *
     * @return true if no ships remain afloat, false otherwise
     */
    public boolean areAllShipsSunk() {
        return shipsRemaining == 0;
    }
}
