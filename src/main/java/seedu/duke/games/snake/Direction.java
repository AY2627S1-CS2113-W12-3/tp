package seedu.duke.games.snake;

/**
 * Represents the four cardinal directions the snake can move.
 *
 * <p>Each direction stores row and column deltas that, when added to a position,
 * produce the next cell in that direction. For example, {@code RIGHT} has
 * {@code rowDelta = 0} and {@code colDelta = 1}, so adding it to {@code (r, c)}
 * gives {@code (r, c + 1)}.
 */
public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    /** The row change when moving in this direction. */
    final int rowDelta;
    /** The column change when moving in this direction. */
    final int colDelta;

    Direction(int rowDelta, int colDelta) {
        this.rowDelta = rowDelta;
        this.colDelta = colDelta;
    }

    /**
     * Returns the direction opposite to this one.
     *
     * <p>Used to prevent the snake from reversing into itself — a move in the
     * opposite direction of travel is ignored.
     *
     * @return the opposite direction
     */
    public Direction opposite() {
        return switch (this) {
        case UP -> DOWN;
        case DOWN -> UP;
        case LEFT -> RIGHT;
        case RIGHT -> LEFT;
        };
    }
}
