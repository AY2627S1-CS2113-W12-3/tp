package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import seedu.duke.battleship.BattleshipGame;
import seedu.duke.battleship.battleshipboard.BattleshipBoard;

/** Tests Battleship commands, attack validation, turns, and resetting. */
class BattleshipGameTest {
    private static final String INVALID_COORDINATE = "Please enter a valid coordinate from A-G and 1-7";
    private static final String REPEATED_COORDINATE = "You have already hit that cell. Try a different coordinate.";

    @Test
    void invalidCoordinatesAreRejected() {
        BattleshipGame game = new BattleshipGame("3");

        for (String coordinate : new String[] {"H1", "A8", "A0", "11", "A", "", "A10"}) {
            assertEquals(INVALID_COORDINATE, game.handleInput(coordinate));
        }
        assertFalse(game.isFinished());
    }

    @Test
    void validCoordinateTriggersPlayerAndComputerTurns() {
        BattleshipGame game = new BattleshipGame("3");

        String response = game.handleInput("A1");

        assertTrue(response.contains("Your shot at A1:"));
        assertTrue(response.contains("Computer shot at "));
        assertFalse(game.isFinished());
    }

    @Test
    void lowercaseAndSpacedCoordinatesAreAccepted() {
        BattleshipGame game = new BattleshipGame("3");

        String response = game.handleInput(" b 4 ");

        assertTrue(response.contains("Your shot at B4:"));
    }

    @Test
    void repeatedCoordinateDoesNotTriggerAnotherTurn() {
        BattleshipGame game = new BattleshipGame("3");
        game.handleInput("A1");

        assertEquals(REPEATED_COORDINATE, game.handleInput("a1"));
        assertFalse(game.isFinished());
    }

    @Test
    void invalidInputDoesNotUseUpACoordinate() {
        BattleshipGame game = new BattleshipGame("3");
        assertEquals(INVALID_COORDINATE, game.handleInput("H1"));

        String response = game.handleInput("A1");

        assertTrue(response.contains("Your shot at A1:"));
        assertTrue(response.contains("Computer shot at "));
    }

    @Test
    void boardCommandShowsBothBoardsWithoutTakingATurn() {
        BattleshipGame game = new BattleshipGame("3");

        String response = game.handleInput("board");

        assertTrue(response.contains("Your board:"));
        assertTrue(response.contains("Enemy's board:"));
        assertTrue(response.contains("S"));
        assertFalse(response.contains("Computer shot at "));
        assertFalse(game.isFinished());
    }

    @Test
    void boardCommandDoesNotChangeBoard() {
        BattleshipGame game = new BattleshipGame("3");

        String before = game.handleInput("board");
        String after = game.handleInput("BOARD");

        assertEquals(before, after);
    }

    @Test
    void quitCommandFinishesGameAndReturnsToMenu() {
        BattleshipGame game = new BattleshipGame("3");

        String response = game.handleInput("quit");

        assertTrue(response.contains("Returning to the main menu."));
        assertTrue(game.isFinished());
        assertEquals(0, game.getPointsEarnedForLastAction());
    }

    @Test
    void quitCommandIsCaseInsensitive() {
        BattleshipGame game = new BattleshipGame("3");

        game.handleInput("QUIT");

        assertTrue(game.isFinished());
    }

    @Test
    void resetRestartsGameAndClearsPreviousShots() {
        BattleshipGame game = new BattleshipGame("3");
        game.handleInput("A1");
        game.handleInput("quit");
        assertTrue(game.isFinished());

        game.reset();

        assertFalse(game.isFinished());
        String response = game.handleInput("A1");
        assertTrue(response.contains("Your shot at A1:"));
        assertTrue(response.contains("Computer shot at "));
    }

    @Test
    void gameProvidesCorrectIdentityAndInstructions() {
        BattleshipGame game = new BattleshipGame("3");

        assertEquals("3", game.getGameId());
        assertEquals("Battleship", game.getGameName());
        assertTrue(game.getStartMessage().contains("A1 to G7"));
        assertTrue(game.getStartMessage().contains("4 enemy ships"));
    }

    @Test
    void quitCommandDisplaysGameStatistics() {
        BattleshipGame game = new BattleshipGame("3");

        String response = game.handleInput("quit");

        assertTrue(response.contains("Rounds played: 0"));
        assertTrue(response.contains("Successful hits: 0"));
        assertTrue(response.contains("Missed shots: 0"));
        assertTrue(response.contains("Accuracy: 0.00%"));
        assertTrue(game.isFinished());
    }

    @Test
    void winningAwardsFivePoints() {
        long seed = 2024;
        Random layoutRandom = new Random(seed);
        new BattleshipBoard(layoutRandom);
        BattleshipBoard enemyBoard = new BattleshipBoard(layoutRandom);
        BattleshipGame game = new BattleshipGame("3", new Random(seed));

        for (String coordinate : coordinatesWithShips(enemyBoard)) {
            game.handleInput(coordinate);
            if (game.isFinished()) {
                break;
            }
        }

        assertTrue(game.isFinished());
        assertEquals(5, game.getPointsEarnedForLastAction());
    }

    @Test
    void invalidInputDoesNotAwardPoints() {
        BattleshipGame game = new BattleshipGame("3", new Random(42));

        game.handleInput("H1");

        assertEquals(0, game.getPointsEarnedForLastAction());
    }

    @Test
    void computerWinDoesNotAwardPoints() {
        for (long seed = 0; seed < 100; seed++) {
            Random layoutRandom = new Random(seed);
            new BattleshipBoard(layoutRandom);
            BattleshipBoard enemyBoard = new BattleshipBoard(layoutRandom);
            BattleshipGame game = new BattleshipGame("3", new Random(seed));

            for (String coordinate : coordinatesWithoutShips(enemyBoard)) {
                game.handleInput(coordinate);
                if (game.isFinished()) {
                    break;
                }
            }

            if (game.isFinished()) {
                assertEquals(0, game.getPointsEarnedForLastAction());
                return;
            }
        }

        assertTrue(false, "A deterministic test game should let the computer win.");
    }

    /** Returns enemy ship coordinates from a board with a known random layout. */
    private static List<String> coordinatesWithShips(BattleshipBoard board) {
        String[] rows = board.stringFormattedBoard(false).split(System.lineSeparator());
        List<String> coordinates = new ArrayList<>();
        for (int row = 0; row < BattleshipBoard.SIDE_LENGTH; row++) {
            String[] cells = rows[row + 1].trim().split("\\s+");
            for (int column = 1; column <= BattleshipBoard.SIDE_LENGTH; column++) {
                if (cells[column].equals("S")) {
                    coordinates.add(String.valueOf((char) ('A' + row)) + column);
                }
            }
        }
        return coordinates;
    }

    /** Returns empty enemy-board coordinates for a game that the computer can win. */
    private static List<String> coordinatesWithoutShips(BattleshipBoard board) {
        String[] rows = board.stringFormattedBoard(false).split(System.lineSeparator());
        List<String> coordinates = new ArrayList<>();
        for (int row = 0; row < BattleshipBoard.SIDE_LENGTH; row++) {
            String[] cells = rows[row + 1].trim().split("\\s+");
            for (int column = 1; column <= BattleshipBoard.SIDE_LENGTH; column++) {
                if (cells[column].equals("~")) {
                    coordinates.add(String.valueOf((char) ('A' + row)) + column);
                }
            }
        }
        return coordinates;
    }
}
