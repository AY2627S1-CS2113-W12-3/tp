package seedu.duke.games.snake;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DirectionTest {

    @Test
    void opposite_returnsCorrectOpposite() {
        assertEquals(Direction.DOWN, Direction.UP.opposite());
        assertEquals(Direction.UP, Direction.DOWN.opposite());
        assertEquals(Direction.RIGHT, Direction.LEFT.opposite());
        assertEquals(Direction.LEFT, Direction.RIGHT.opposite());
    }

    @Test
    void deltas_upMovesRowNegative() {
        assertEquals(-1, Direction.UP.rowDelta);
        assertEquals(0, Direction.UP.colDelta);
    }

    @Test
    void deltas_downMovesRowPositive() {
        assertEquals(1, Direction.DOWN.rowDelta);
        assertEquals(0, Direction.DOWN.colDelta);
    }

    @Test
    void deltas_leftMovesColNegative() {
        assertEquals(0, Direction.LEFT.rowDelta);
        assertEquals(-1, Direction.LEFT.colDelta);
    }

    @Test
    void deltas_rightMovesColPositive() {
        assertEquals(0, Direction.RIGHT.rowDelta);
        assertEquals(1, Direction.RIGHT.colDelta);
    }
}
