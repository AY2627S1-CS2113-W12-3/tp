package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.duke.battleship.BattleshipGame;

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

        assertEquals("Returning to the main menu.", response);
        assertTrue(game.isFinished());
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
}
