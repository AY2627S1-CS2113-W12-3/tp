package seedu.duke.games.snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

class SnakeGameTest {

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
    void metadata_returnsExpectedValues() {
        SnakeGame game = new SnakeGame("6", new SeededRandom(0, 5));
        assertEquals("6", game.getGameId());
        assertEquals("Snake", game.getGameName());
        assertFalse(game.getDescription().isEmpty());
        assertFalse(game.getStartMessage().isEmpty());
        assertTrue(game.getStartMessage().contains("@"));
    }

    @Test
    void handleInput_validMoveReturnsBoardAndScore() {
        // Food at (0, 5), safely away from the snake.
        SnakeGame game = new SnakeGame("6", new SeededRandom(0, 5));
        String response = game.handleInput("d");
        assertTrue(response.contains("@"));
        assertTrue(response.contains("Score: 0"));
        assertFalse(game.isFinished());
    }

    @Test
    void handleInput_forwardCommandMovesSnake() {
        SnakeGame game = new SnakeGame("6", new SeededRandom(0, 5));
        String response = game.handleInput("f");
        assertTrue(response.contains("Score: 0"));
        assertFalse(game.isFinished());
    }

    @Test
    void handleInput_invalidInputReturnsError() {
        SnakeGame game = new SnakeGame("6", new SeededRandom(0, 5));
        String response = game.handleInput("x");
        assertTrue(response.contains("Invalid command"));
        assertFalse(game.isFinished());
    }

    @Test
    void handleInput_reversalRejected() {
        // Starting direction is RIGHT; 'a' (LEFT) is the opposite.
        SnakeGame game = new SnakeGame("6", new SeededRandom(0, 5));
        String response = game.handleInput("a");
        assertTrue(response.contains("can't reverse"));
        assertFalse(game.isFinished());
    }

    @Test
    void handleInput_eatingFoodAwardsPoints() {
        // Food at (4, 3) — one step to the right of the head at (4, 2).
        // After eating, new food placed at (0, 0).
        SnakeGame game = new SnakeGame("6", new SeededRandom(4, 3, 0, 0));
        String response = game.handleInput("d");
        assertTrue(response.contains("Yum!"));
        assertTrue(response.contains("Score: 1"));
        assertEquals(1, game.getPointsEarnedForLastAction());
    }

    @Test
    void handleInput_wallCollisionEndsGame() {
        // Food at (0, 5) — out of the way.
        SnakeGame game = new SnakeGame("6", new SeededRandom(0, 5));
        // Move right 7 times (head at col 2 -> col 9), then one more hits the wall.
        for (int i = 0; i < 7; i++) {
            game.handleInput("d");
        }
        String response = game.handleInput("d");
        assertTrue(response.contains("Game Over"));
        assertTrue(game.isFinished());
    }

    @Test
    void handleInput_quitEndsGame() {
        SnakeGame game = new SnakeGame("6", new SeededRandom(0, 5));
        String response = game.handleInput("quit");
        assertTrue(response.contains("Final score: 0"));
        assertTrue(response.contains("Returning to the main menu."));
        assertTrue(game.isFinished());
    }

    @Test
    void handleInput_afterGameOverReturnsEndedMessage() {
        SnakeGame game = new SnakeGame("6", new SeededRandom(0, 5));
        game.handleInput("quit");
        String response = game.handleInput("d");
        assertTrue(response.contains("game has ended"));
    }

    @Test
    void reset_clearsFinishedStateAndScore() {
        SnakeGame game = new SnakeGame("6", new SeededRandom(
                4, 3,   // initial food (eaten immediately)
                0, 0,   // food after eating
                0, 0    // food after reset
        ));
        game.handleInput("d"); // eat food, score = 1
        game.handleInput("quit");
        assertTrue(game.isFinished());

        game.reset();
        assertFalse(game.isFinished());
        assertEquals(0, game.getPointsEarnedForLastAction());
    }

    @Test
    void handleInput_nullInputReturnsError() {
        SnakeGame game = new SnakeGame("6", new SeededRandom(0, 5));
        String response = game.handleInput(null);
        assertTrue(response.contains("Invalid command"));
    }
}
