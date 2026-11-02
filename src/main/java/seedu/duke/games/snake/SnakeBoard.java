package seedu.duke.games.snake;

import java.util.LinkedList;
import java.util.Random;

/**
 * Holds the grid, snake body, food position, and movement logic for a snake game.
 *
 * <p>The board is a {@value #HEIGHT} x {@value #WIDTH} grid. The snake is stored as
 * a linked list of {@code [row, col]} positions where the first element is the head.
 * Each call to {@link #advance()} moves the snake one step in the current direction.
 */
public class SnakeBoard {
    /** Number of rows in the playing area. */
    static final int HEIGHT = 10;
    /** Number of columns in the playing area. */
    static final int WIDTH = 10;

    /** Character used to render the snake's head. */
    private static final char HEAD_CHAR = '@';
    /** Character used to render the snake's body segments. */
    private static final char BODY_CHAR = '#';
    /** Character used to render the food. */
    private static final char FOOD_CHAR = '*';
    /** Character used to render empty cells. */
    private static final char EMPTY_CHAR = '.';

    /** The snake body. Head is {@code getFirst()}, tail is {@code getLast()}. */
    private LinkedList<int[]> body;
    /** The direction the snake is currently travelling. */
    private Direction currentDirection;
    /** Row of the food item. */
    private int foodRow;
    /** Column of the food item. */
    private int foodCol;
    /** Whether the snake has collided and the game is over. */
    private boolean isGameOver;
    /** Whether the snake fills the entire board (win condition). */
    private boolean boardFull;
    /** Random source for food placement — injectable for tests. */
    private final Random random;

    /** The result of a single step of snake movement. */
    public enum MoveResult {
        /** The snake moved normally without eating or colliding. */
        MOVED,
        /** The snake ate food and grew by one segment. */
        ATE_FOOD,
        /** The snake hit a wall or its own body. */
        COLLISION
    }

    /**
     * Creates a board with the snake starting near the left-centre, heading right.
     *
     * @param random the random source used for food placement
     */
    public SnakeBoard(Random random) {
        this.random = random;
        initializeState();
    }

    /**
     * Attempts to change the snake's direction.
     *
     * <p>The change is rejected if the new direction is the opposite of the current
     * one, which would cause an immediate self-collision.
     *
     * @param newDirection the direction to change to
     * @return {@code true} if the direction was accepted, {@code false} if rejected
     */
    public boolean tryChangeDirection(Direction newDirection) {
        if (newDirection == currentDirection.opposite()) {
            return false;
        }
        currentDirection = newDirection;
        return true;
    }

    /**
     * Moves the snake one step in the current direction.
     *
     * <p>If the new head position is out of bounds or overlaps the body, the game
     * ends with a {@link MoveResult#COLLISION}. If the head lands on food, the snake
     * grows and new food is placed. Otherwise the snake simply slides forward.
     *
     * @return the result of this move
     */
    public MoveResult advance() {
        int[] head = body.getFirst();
        int newRow = head[0] + currentDirection.rowDelta;
        int newCol = head[1] + currentDirection.colDelta;

        if (isOutOfBounds(newRow, newCol) || isOnSnakeBody(newRow, newCol)) {
            isGameOver = true;
            return MoveResult.COLLISION;
        }

        body.addFirst(new int[]{newRow, newCol});

        if (newRow == foodRow && newCol == foodCol) {
            if (body.size() < HEIGHT * WIDTH) {
                placeFood();
            } else {
                boardFull = true;
            }
            return MoveResult.ATE_FOOD;
        }

        body.removeLast();
        return MoveResult.MOVED;
    }

    /**
     * Renders the board as an ASCII string.
     *
     * <p>The grid is surrounded by a border of {@code +}, {@code -}, and {@code |}
     * characters. The snake head is shown as {@code @}, body as {@code #}, food as
     * {@code *}, and empty cells as {@code .}.
     *
     * @return the rendered board
     */
    public String render() {
        char[][] grid = new char[HEIGHT][WIDTH];
        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                grid[r][c] = EMPTY_CHAR;
            }
        }

        for (int i = 1; i < body.size(); i++) {
            int[] segment = body.get(i);
            grid[segment[0]][segment[1]] = BODY_CHAR;
        }
        int[] head = body.getFirst();
        grid[head[0]][head[1]] = HEAD_CHAR;
        if (!boardFull) {
            grid[foodRow][foodCol] = FOOD_CHAR;
        }

        StringBuilder sb = new StringBuilder();
        String horizontalBorder = "+" + "-".repeat(WIDTH) + "+";
        sb.append(horizontalBorder).append(System.lineSeparator());
        for (int r = 0; r < HEIGHT; r++) {
            sb.append('|');
            for (int c = 0; c < WIDTH; c++) {
                sb.append(grid[r][c]);
            }
            sb.append('|').append(System.lineSeparator());
        }
        sb.append(horizontalBorder);
        return sb.toString();
    }

    /** Returns whether the game is over due to a collision. */
    public boolean isGameOver() {
        return isGameOver;
    }

    /** Returns whether the snake fills the entire board (win condition). */
    public boolean isBoardFull() {
        return boardFull;
    }

    /** Returns the current direction the snake is travelling. */
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    /** Returns the number of segments in the snake (including the head). */
    public int getSnakeLength() {
        return body.size();
    }

    /**
     * Resets the board to its initial state so the game can be replayed.
     */
    public void reset() {
        initializeState();
    }

    /**
     * Sets up the starting snake position, direction, and first food placement.
     *
     * <p>The snake starts with 3 segments near the left side of row 4, heading right.
     */
    private void initializeState() {
        body = new LinkedList<>();
        body.add(new int[]{4, 2}); // head
        body.add(new int[]{4, 1});
        body.add(new int[]{4, 0}); // tail
        currentDirection = Direction.RIGHT;
        isGameOver = false;
        boardFull = false;
        placeFood();
    }

    /**
     * Places food on a random empty cell.
     *
     * <p>Keeps trying random positions until one is found that is not occupied by
     * the snake. On a 10x10 board with typical snake lengths this rarely retries.
     */
    private void placeFood() {
        int row;
        int col;
        do {
            row = random.nextInt(HEIGHT);
            col = random.nextInt(WIDTH);
        } while (isOnSnake(row, col));
        foodRow = row;
        foodCol = col;
    }

    private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= HEIGHT || col < 0 || col >= WIDTH;
    }

    /** Checks whether the given position overlaps any part of the snake. */
    private boolean isOnSnake(int row, int col) {
        for (int[] segment : body) {
            if (segment[0] == row && segment[1] == col) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the given position overlaps the snake body (excluding the tail
     * that is about to be removed).
     *
     * <p>The tail is excluded because in a normal move, the tail is removed after
     * the head advances. Including it would cause a false self-collision when the
     * snake chases its own tail.
     */
    private boolean isOnSnakeBody(int row, int col) {
        for (int i = 0; i < body.size() - 1; i++) {
            int[] segment = body.get(i);
            if (segment[0] == row && segment[1] == col) {
                return true;
            }
        }
        return false;
    }
}
