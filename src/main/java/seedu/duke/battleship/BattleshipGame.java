package seedu.duke.battleship;

import seedu.duke.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Logger;

public class BattleshipGame implements Game {
    private static final Logger LOGGER = Logger.getLogger(BattleshipGame.class.getName());
    private final Random random;

    private final String gameId;
    private final String gameName = "Battleship";
    private boolean isGameOver = false;

    private BattleshipBoard playerBoard;
    private BattleshipBoard enemyBoard;

    private ArrayList<Integer> computerTargets;

    private int roundsPlayed = 0;

    private static final String QUIT_COMMAND = "quit";
    private static final String BOARD_COMMAND = "board";
    private final String EXIT_GAME_MESSAGE = "Returning to the main menu.";
    private int nextComputerTarget;


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
        return "Sink all 3 enemy ships before the computer sinks yours!" + System.lineSeparator()
                + "Ships: lengths 4, 3, and 2. Ships are placed automatically." + System.lineSeparator()
                + "Fire by typing a coordinate from A1 to H8 (for example, B4)." + System.lineSeparator()
                + "Type 'board' to see both boards." + System.lineSeparator()
                + "Type 'quit' to leave." + System.lineSeparator()
                + "Legend: S = your ship, X = hit, O = miss, ~ = unknown";
    }


    @Override
    public String getExitMessage() {
        return Game.super.getExitMessage();
    }

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

        if (!argument.matches("[A-Ja-j]\\s*[0-9]")) {
//            return BattleshipException.invalidCoordinate();
            return null;
        }

        // Add error handling here
        int row = argument.charAt(0) - 'A';
        int col = argument.charAt(1) - '0';

        String roundResult = executePlayerTurn(row, col);

        if (!isFinished()) {
            roundResult += executeEnemyTurn();
        }

        return roundResult;
    }

    private String getBoardDisplays() {
        return "Your board:"
                + System.lineSeparator()
                + playerBoard.stringFormattedBoard(false)
                + System.lineSeparator()
                + "Enemy's board:"
                + System.lineSeparator()
                + enemyBoard.stringFormattedBoard(true);
    }

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
            message.append("You win! All enemy ships have been sunk in ")
                    .append(roundsPlayed).append(" rounds.")
                    .append(System.lineSeparator())
                    .append(EXIT_GAME_MESSAGE);

        }

        return message.toString();
    }

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
            message.append("The computer wins. All your ships have been sunk in")
                    .append(roundsPlayed).append(" rounds.")
                    .append(System.lineSeparator())
                    .append(EXIT_GAME_MESSAGE);
        }
        
        return message.toString();
    }

    private static String formatCoordinate(int row, int col) {
        return "{" + (char) ('A' + row) + col + "}";
    }

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


    @Override
    public boolean isFinished() {
        return isGameOver;
    }

    @Override
    public void reset() {
        isGameOver = false;
        playerBoard = new BattleshipBoard(random);
        enemyBoard = new BattleshipBoard(random);
        initialiseComputerTargets();
    }

    void initialiseComputerTargets() {
        computerTargets = new ArrayList<>();

        int totalTargets = BattleshipBoard.SIDE_LENGTH * BattleshipBoard.SIDE_LENGTH;
        for (int i = 0; i < totalTargets; i++) {
            computerTargets.add(i);
        }
        
        Collections.shuffle(computerTargets, random);
        nextComputerTarget = 0;
    }
}
