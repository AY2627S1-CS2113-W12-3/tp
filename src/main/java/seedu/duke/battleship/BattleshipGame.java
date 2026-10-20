package seedu.duke.battleship;

import seedu.duke.Game;
import seedu.duke.battleship.battleshipboard.BattleshipBoard;
import seedu.duke.battleship.battleshipboard.ShotResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Provides a Battleship game where the player competes against a computer.
 *
 * <p>Both players have a 7x7 board containing four randomly placed ships.
 * Players take turns attacking coordinates until all ships belonging to
 * one player have been sunk.
 */
public class BattleshipGame implements Game {
    /** Records diagnostic events during gameplay. */
    private static final Logger LOGGER = Logger.getLogger(BattleshipGame.class.getName());

    /** Random number generator used for ship placement and computer attacks. */
    private final Random random;

    private final String gameId;
    private final String gameName = "Battleship";
    private boolean isGameOver = false;

    private BattleshipBoard playerBoard;
    private BattleshipBoard enemyBoard;
    private int roundsPlayed = 0;

    /** Shuffled list of coordinates available for computer attacks. */
    private ArrayList<Integer> computerTargets;
    private int nextComputerTarget;


    private static final String QUIT_COMMAND = "quit";
    private static final String BOARD_COMMAND = "board";
    private final String EXIT_GAME_MESSAGE = "Returning to the main menu.";


    /**
     * Creates a Battleship game with the given game ID.
     * Initialises the random number generator and prepares a new game.
     *
     * @param gameId the ID used to select this game
     */
    public BattleshipGame(String gameId) {
        this.gameId = gameId;
        random = new Random();
        reset();
    }

    @Override
    public String getGameId() {
        return gameId;
    }

    @Override
    public String getGameName() {
        return gameName;
    }

    @Override
    public String getStartMessage() {
        return "Sink all 4 enemy ships before the computer sinks yours!" + System.lineSeparator()
                + "Ships: lengths 4, 3, 2 and 1. Ships are placed automatically." + System.lineSeparator()
                + "Fire by typing a coordinate from A1 to G7 (for example, B4)." + System.lineSeparator()
                + "Type 'board' to see both boards." + System.lineSeparator()
                + "Type 'quit' to leave." + System.lineSeparator()
                + "Legend: S = your ship, X = miss, O = hit, ~ = unknown";
    }


    @Override
    public String getExitMessage() {
        return Game.super.getExitMessage();
    }

    /**
     * Processes a player command or attack coordinate.
     *
     * <p>Validates the coordinate before attacking the enemy board.
     * Invalid or repeated coordinates do not consume a turn.
     * After a valid attack, the computer takes its turn unless
     * the player has already won.
     *
     * @param input the command or coordinate entered by the player
     * @return the command response, validation message or round results
     */
    @Override
    public String handleInput(String input) {
        String argument = input == null ? "" : input.trim();

        if (argument.equalsIgnoreCase(QUIT_COMMAND)) {
            LOGGER.info("Player quit Battleship after " + roundsPlayed + " rounds.");
            isGameOver = true;
            return EXIT_GAME_MESSAGE;
        }

        if (argument.equalsIgnoreCase(BOARD_COMMAND)) {
            return getBoardDisplays();
        }

        if (!argument.matches("[A-Ga-g]\\s*[1-7]")) {
            return BattleshipException.invalidCoordinate();
        }

        String coordinate = argument.replaceAll("\\s+", "").toUpperCase();
        int row = coordinate.charAt(0) - 'A';
        int col = coordinate.charAt(1) - '1';

        assert((row >= 0 && row < 7));
        assert((col >= 0 && col < 7));

        if (enemyBoard.isCellHit(row, col)) {
            return BattleshipException.repeatedCoordinate();
        }

        assert(!enemyBoard.isCellHit(row, col));

        String roundResult = executePlayerTurn(row, col);
        if (!isGameOver) {
            roundResult += executeEnemyTurn();
        }

        return roundResult;
    }

    /**
     * Builds a formatted display of the player's and computer's boards.
     *
     * <p>The player's ships are visible, while the computer's
     * unhit ships remain hidden.
     *
     * @return the formatted representations of both boards
     */
    private String getBoardDisplays() {
        return "Your board:"
                + System.lineSeparator()
                + playerBoard.stringFormattedBoard(false)
                + System.lineSeparator()
                + "Enemy's board:"
                + System.lineSeparator()
                + enemyBoard.stringFormattedBoard(true);
    }

    /**
     * Executes the player's attack against the computer's board.
     *
     * <p>Increments the round counter and checks whether all enemy
     * ships have been sunk after the attack.
     *
     * @param row the zero-based row of the target cell
     * @param col the zero-based column of the target cell
     * @return the attack result and a victory message if the player wins
     */

    private String executePlayerTurn(int row, int col) {
        roundsPlayed++;

        ShotResult playerResult = enemyBoard.hitAt(row, col);

        StringBuilder message = new StringBuilder();
        message.append("Your shot at ")
                .append(formatCoordinate(row, col))
                .append(": ")
                .append(formatShotResult(playerResult))
                .append(System.lineSeparator());

        if (enemyBoard.allShipsSunk()) {
            isGameOver = true;
            LOGGER.info("Player won after " + roundsPlayed + ".");
            message.append("You win! All enemy ships have been sunk in ")
                    .append(roundsPlayed).append(" rounds.")
                    .append(System.lineSeparator())
                    .append(EXIT_GAME_MESSAGE);

        }

        return message.toString();
    }

    /**
     * Executes the computer's attack against the player's board.
     *
     * <p>The computer selects the next coordinate from a shuffled
     * list, ensuring that it does not attack the same cell twice.
     * Checks whether all player ships have been sunk after the attack.
     *
     * @return the computer's attack result and a defeat message if the player loses
     */
    private String executeEnemyTurn() {
        int target = computerTargets.get(nextComputerTarget++);
        int row = target / BattleshipBoard.SIDE_LENGTH;
        int col = target % BattleshipBoard.SIDE_LENGTH;

        ShotResult computerResult = playerBoard.hitAt(row, col);
        
        StringBuilder message = new StringBuilder();
        message.append("Computer shot at ")
                .append(formatCoordinate(row, col))
                .append(": ")
                .append(formatShotResult(computerResult))
                .append(System.lineSeparator());

        if (playerBoard.allShipsSunk()) {
            isGameOver = true;
            LOGGER.info("Player lost after " + roundsPlayed + ".");
            message.append("The computer wins. All your ships have been sunk in ")
                    .append(roundsPlayed).append(" rounds.")
                    .append(System.lineSeparator())
                    .append(EXIT_GAME_MESSAGE);
        }
        
        return message.toString();
    }

    /**
     * Converts zero-based row and column indices into a board coordinate.
     *
     * @param row the zero-based row index
     * @param col the zero-based column index
     * @return the formatted coordinate, such as A1 or G7
     */
    private static String formatCoordinate(int row, int col) {
        return "" + (char) ('A' + row) + (col + 1);
    }

    /**
     * Converts an attack result into a user-facing message.
     *
     * @param result the result of an attack
     * @return the message describing a miss, hit or sunk ship
     * @throws IllegalStateException if the result is not recognised
     */

    private static String formatShotResult(ShotResult result) {
        switch (result) {
            case MISS:
                return "Miss.";
            case HIT:
                return "Hit!";
            case SUNK:
                return "Hit and sunk a ship!";
            default:
                throw new IllegalStateException("Unhandled shot result: " + result);
        }
    }

    /**
     * Prepares a shuffled list of all possible computer attack coordinates.
     *
     * <p>Each cell is represented by a unique integer, ensuring
     * that the computer attacks every coordinate at most once.
     * Resets the target index to the beginning of the shuffled list.
     */
    void initialiseComputerTargets() {
        computerTargets = new ArrayList<>();

        int totalTargets = BattleshipBoard.SIDE_LENGTH * BattleshipBoard.SIDE_LENGTH;
        for (int i = 0; i < totalTargets; i++) {
            computerTargets.add(i);
        }

        Collections.shuffle(computerTargets, random);
        nextComputerTarget = 0;
    }

    /**
     * Checks whether the current Battleship game has ended.
     *
     * @return true if the game has ended, false otherwise
     */
    @Override
    public boolean isFinished() {
        return isGameOver;
    }

    /**
     * Resets the game to its initial state for a new session.
     *
     * <p>Clears the game-over status and round counter, generates
     * new boards with randomly placed ships, and prepares a new
     * shuffled list of computer targets.
     */
    @Override
    public void reset() {
        isGameOver = false;
        roundsPlayed = 0;
        playerBoard = new BattleshipBoard(random);
        enemyBoard = new BattleshipBoard(random);
        initialiseComputerTargets();
    }
}
