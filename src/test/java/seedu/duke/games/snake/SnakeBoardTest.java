package seedu.duke.games.snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

class SnakeBoardTest {

    /**
     * A Random subclass that returns a fixed sequence of values.
     * Used to make food placement deterministic in tests.
     */
    private static class SeededRandom extends Random {
        private final int[] values;
        private int index;

        SeededRandom(int... values) {
            this.values = values;
            this.index = 0;
        }

        @Override
        public int nextInt(int bound) {
            int value = values[index % values.length];
            index++;
            return value % bound;
        }
    }

    @Test
    void initialState_snakeStartsAtExpectedPosition() {
        // Food placed at (0, 0) via seeded random so it doesn't overlap the snake.
        SnakeBoard board = new SnakeBoard(new SeededRandom(0, 0));
        assertEquals(3, board.getSnakeLength());
        assertEquals(Direction.RIGHT, board.getCurrentDirection());
        assertFalse(board.isGameOver());
    }

    @Test
    void render_initialBoardContainsSnakeAndFood() {
        SnakeBoard board = new SnakeBoard(new SeededRandom(0, 5));
        String rendered = board.render();
        // Head at row 4, col 2 should be '@'
        assertTrue(rendered.contains("@"));
        // Body segments should be '#'
        assertTrue(rendered.contains("#"));
        // Food should be '*'
        assertTrue(rendered.contains("*"));
        // Border characters
        assertTrue(rendered.contains("+"));
        assertTrue(rendered.contains("|"));
    }

    @Test
    void advance_snakeMovesForward() {
        // Place food at (0, 5) so the snake doesn't eat it immediately.
        SnakeBoard board = new SnakeBoard(new SeededRandom(0, 5));
        SnakeBoard.MoveResult result = board.advance();
        assertEquals(SnakeBoard.MoveResult.MOVED, result);
        assertEquals(3, board.getSnakeLength());
        assertFalse(board.isGameOver());
    }

    @Test
    void advance_eatingFoodGrowsSnake() {
        // Snake head starts at (4, 2) going RIGHT, so next position is (4, 3).
        // Place food at (4, 3) so the snake eats it on the first move.
        // After eating, placeFood is called again — supply (0, 0) for the new food.
        SnakeBoard board = new SnakeBoard(new SeededRandom(4, 3, 0, 0));
        SnakeBoard.MoveResult result = board.advance();
        assertEquals(SnakeBoard.MoveResult.ATE_FOOD, result);
        assertEquals(4, board.getSnakeLength());
    }

    @Test
    void advance_wallCollisionEndsGame() {
        // Snake starts at (4, 2) going RIGHT. Move it to the right wall.
        SnakeBoard board = new SnakeBoard(new SeededRandom(0, 0));
        // Move right 7 times to reach col 9, then one more to hit the wall.
        for (int i = 0; i < 7; i++) {
            assertEquals(SnakeBoard.MoveResult.MOVED, board.advance());
        }
        SnakeBoard.MoveResult result = board.advance();
        assertEquals(SnakeBoard.MoveResult.COLLISION, result);
        assertTrue(board.isGameOver());
    }

    @Test
    void advance_topWallCollision() {
        SnakeBoard board = new SnakeBoard(new SeededRandom(0, 5));
        board.tryChangeDirection(Direction.UP);
        // Snake head at row 4, move up 4 times to reach row 0, then one more.
        for (int i = 0; i < 4; i++) {
            assertEquals(SnakeBoard.MoveResult.MOVED, board.advance());
        }
        assertEquals(SnakeBoard.MoveResult.COLLISION, board.advance());
        assertTrue(board.isGameOver());
    }

    @Test
    void tryChangeDirection_rejectsOpposite() {
        SnakeBoard board = new SnakeBoard(new SeededRandom(0, 0));
        // Starting direction is RIGHT; LEFT is the opposite.
        assertFalse(board.tryChangeDirection(Direction.LEFT));
        assertEquals(Direction.RIGHT, board.getCurrentDirection());
    }

    @Test
    void tryChangeDirection_acceptsPerpendicular() {
        SnakeBoard board = new SnakeBoard(new SeededRandom(0, 0));
        assertTrue(board.tryChangeDirection(Direction.UP));
        assertEquals(Direction.UP, board.getCurrentDirection());
    }

    @Test
    void reset_restoresInitialState() {
        SnakeBoard board = new SnakeBoard(new SeededRandom(0, 0, 0, 0));
        board.advance();
        board.advance();
        board.reset();
        assertEquals(3, board.getSnakeLength());
        assertEquals(Direction.RIGHT, board.getCurrentDirection());
        assertFalse(board.isGameOver());
    }

    @Test
    void advance_selfCollisionEndsGame() {
        // Grow the snake, then steer it into itself.
        // Place food directly ahead each time to grow: (4,3), (4,4), (4,5), (4,6)
        // After eating, new food is placed — supply safe positions.
        SnakeBoard board = new SnakeBoard(new SeededRandom(
                4, 3,   // initial food
                4, 4,   // food after first eat
                4, 5,   // food after second eat
                4, 6,   // food after third eat
                0, 0    // food after fourth eat (safe spot)
        ));
        // Eat 4 pieces of food to grow to length 7.
        for (int i = 0; i < 4; i++) {
            assertEquals(SnakeBoard.MoveResult.ATE_FOOD, board.advance());
        }
        assertEquals(7, board.getSnakeLength());
        // Now turn down, left, then up to loop back into the body.
        board.tryChangeDirection(Direction.DOWN);
        board.advance(); // (5, 6)
        board.tryChangeDirection(Direction.LEFT);
        board.advance(); // (5, 5)
        board.tryChangeDirection(Direction.UP);
        // Moving up from (5, 5) goes to (4, 5) which is part of the snake body.
        assertEquals(SnakeBoard.MoveResult.COLLISION, board.advance());
        assertTrue(board.isGameOver());
    }
}
