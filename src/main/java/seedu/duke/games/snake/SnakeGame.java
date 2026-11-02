package seedu.duke.games.snake;

import java.util.Random;

import seedu.duke.Game;

/**
 * A turn-based CLI snake game.
 *
 * <p>Each turn the player types a direction ({@code w}/{@code a}/{@code s}/{@code d})
 * or {@code f} to continue forward, and the snake advances one step. The snake grows
 * when it eats food ({@code *}) and the game ends when it hits a wall or itself.
 */
public class SnakeGame implements Game {
    /** Points awarded each time the snake eats food. */
    private static final int POINTS_PER_FOOD = 1;
    private static final String QUIT_COMMAND = "quit";
    private static final String EXIT_MESSAGE = "Returning to the main menu.";

    private final String gameId;
    private final SnakeBoard board;
    private boolean isFinished;
    private int pointsEarnedForLastAction;
    private int score;

    /**
     * Creates a snake game with the given ID and a default random source.
     *
     * @param gameId the ID used to select this game from the menu
     */
    public SnakeGame(String gameId) {
        this(gameId, new Random());
    }

    /**
     * Creates a snake game with a caller-supplied random source.
     *
     * <p>Tests can supply a repeatable random source to control food placement.
     *
     * @param gameId the ID used to select this game from the menu
     * @param random the random source used for food placement
     */
    public SnakeGame(String gameId, Random random) {
        this.gameId = gameId;
        this.board = new SnakeBoard(random);
    }

    @Override
    public String getGameId() {
        return gameId;
    }

    @Override
    public String getGameName() {
        return "Snake";
    }

    @Override
    public String getDescription() {
        return "Guide the snake to eat food and grow without crashing";
    }

    /**
     * Returns the instructions and initial board shown when the game starts.
     *
     * @return the opening message with controls and the starting board
     */
    @Override
    public String getStartMessage() {
        return "Control the snake to eat food (*) and grow!" + System.lineSeparator()
                + "Commands: w (up), a (left), s (down), d (right), f (forward)"
                + System.lineSeparator()
                + "The snake moves one step each turn. Type 'quit' to leave."
                + System.lineSeparator()
                + System.lineSeparator()
                + board.render();
    }

    @Override
    public String getExitMessage() {
        return EXIT_MESSAGE;
    }

    @Override
    public String getCommands() {
        return "w - move up" + System.lineSeparator()
                + "a - move left" + System.lineSeparator()
                + "s - move down" + System.lineSeparator()
                + "d - move right" + System.lineSeparator()
                + "f - continue forward" + System.lineSeparator()
                + "quit - leave the game";
    }

    /**
     * Processes one line of player input.
     *
     * <p>Valid inputs are {@code w}, {@code a}, {@code s}, {@code d} (change
     * direction), {@code f} (continue forward), or {@code quit} (leave the game).
     * Invalid inputs and reversed directions are rejected without advancing the snake.
     *
     * @param input the line entered by the player
     * @return the game response to display
     */
    @Override
    public String handleInput(String input) {
        pointsEarnedForLastAction = 0;
        if (isFinished) {
            return "The game has ended. Start a new game to play again.";
        }

        String trimmed = input == null ? "" : input.trim().toLowerCase();
        if (trimmed.equals(QUIT_COMMAND)) {
            isFinished = true;
            return "Final score: " + score + System.lineSeparator() + EXIT_MESSAGE;
        }

        Direction direction = parseDirection(trimmed);
        if (direction == null && !trimmed.equals("f")) {
            return "Invalid command. Use w/a/s/d to move, f to go forward, or quit.";
        }

        if (direction != null && !board.tryChangeDirection(direction)) {
            return "You can't reverse direction! Use f to continue forward.";
        }

        SnakeBoard.MoveResult result = board.advance();
        return switch (result) {
        case COLLISION -> handleCollision();
        case ATE_FOOD -> handleFoodEaten();
        case MOVED -> board.render() + System.lineSeparator() + "Score: " + score;
        };
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public int getPointsEarnedForLastAction() {
        return pointsEarnedForLastAction;
    }

    @Override
    public void reset() {
        board.reset();
        isFinished = false;
        pointsEarnedForLastAction = 0;
        score = 0;
    }

    /**
     * Maps a single-character input to a direction.
     *
     * @param input the trimmed, lowercased input
     * @return the corresponding direction, or {@code null} if not a direction key
     */
    private Direction parseDirection(String input) {
        return switch (input) {
        case "w" -> Direction.UP;
        case "a" -> Direction.LEFT;
        case "s" -> Direction.DOWN;
        case "d" -> Direction.RIGHT;
        default -> null;
        };
    }

    private String handleCollision() {
        isFinished = true;
        return "Game Over! The snake crashed." + System.lineSeparator()
                + board.render() + System.lineSeparator()
                + "Final score: " + score + System.lineSeparator()
                + EXIT_MESSAGE;
    }

    private String handleFoodEaten() {
        score++;
        pointsEarnedForLastAction = POINTS_PER_FOOD;
        if (board.isBoardFull()) {
            isFinished = true;
            return "You filled the entire board! Congratulations!" + System.lineSeparator()
                    + board.render() + System.lineSeparator()
                    + "Final score: " + score + System.lineSeparator()
                    + EXIT_MESSAGE;
        }
        return "Yum! Food eaten." + System.lineSeparator()
                + board.render() + System.lineSeparator()
                + "Score: " + score;
    }
}
