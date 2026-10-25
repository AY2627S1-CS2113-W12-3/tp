package seedu.duke.tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

/** Tests the player/computer turns and input handling for Tic Tac Toe. */
class TicTacToeGameTest {

    @Test
    void exposesGameDetailsAndOpeningInstructions() {
        TicTacToeGame game = new TicTacToeGame("4");

        assertEquals("4", game.getGameId());
        assertEquals("Tic Tac Toe", game.getGameName());
        assertTrue(game.getStartMessage().contains("You are X; the computer is O."));
        assertTrue(game.getStartMessage().contains("Enter a square from A1 to C3"));
    }

    @Test
    void computerChoosesAnEmptySquareAfterAValidPlayerMove() {
        SequenceRandom random = new SequenceRandom(0);
        TicTacToeGame game = new TicTacToeGame("4", random);

        String response = game.handleInput("a1");

        assertTrue(response.contains("You played A1 (X)."));
        assertTrue(response.contains("Computer played A2 (O)."));
        assertTrue(response.contains("A X O ."));
        assertEquals(1, random.getCalls());
        assertFalse(game.isFinished());
    }

    @Test
    void invalidAndOccupiedSquaresDoNotTriggerComputerMoves() {
        SequenceRandom random = new SequenceRandom(0);
        TicTacToeGame game = new TicTacToeGame("4", random);

        assertTrue(game.handleInput("Z9").contains("Please enter a valid square"));
        assertEquals(0, random.getCalls());

        game.handleInput("A1");
        assertEquals(1, random.getCalls());
        assertTrue(game.handleInput("A1").contains("already occupied"));
        assertEquals(1, random.getCalls());
    }

    @Test
    void playerWinEndsTheGameBeforeTheComputerMoves() {
        TicTacToeGame game = new TicTacToeGame("4", new SequenceRandom(0, 0));
        game.handleInput("A3");
        game.handleInput("B3");

        String response = game.handleInput("C3");

        assertTrue(response.contains("You win!"));
        assertFalse(response.contains("Computer played"));
        assertTrue(game.isFinished());
    }

    @Test
    void computerWinEndsTheGame() {
        TicTacToeGame game = new TicTacToeGame("4", new SequenceRandom(0, 0, 0));
        game.handleInput("C1");
        game.handleInput("C2");

        String response = game.handleInput("B3");

        assertTrue(response.contains("The computer wins!"));
        assertTrue(game.isFinished());
    }

    @Test
    void fullBoardWithoutAWinIsDraw() {
        TicTacToeGame game = new TicTacToeGame("4", new SequenceRandom(0, 1, 0, 0));
        game.handleInput("A1");
        game.handleInput("A3");
        game.handleInput("B1");
        game.handleInput("C2");

        String response = game.handleInput("C3");

        assertTrue(response.contains("It's a draw!"));
        assertTrue(game.isFinished());
    }

    @Test
    void quitShowsExitMessageAndResetStartsFreshGame() {
        TicTacToeGame game = new TicTacToeGame("4", new SequenceRandom(0));

        assertEquals("Returning to the main menu.", game.handleInput("quit"));
        assertTrue(game.isFinished());
        assertEquals("Returning to the main menu.", game.getExitMessage());

        game.reset();

        assertFalse(game.isFinished());
        assertTrue(game.getStartMessage().contains("A . . ."));
        assertTrue(game.handleInput("A1").contains("Computer played A2 (O)."));
    }

    /** Supplies predictable indexes so computer turns can be checked without relying on luck. */
    private static class SequenceRandom extends Random {
        private static final long serialVersionUID = 1L;

        private final int[] choices;
        private int nextChoice;

        SequenceRandom(int... choices) {
            this.choices = choices;
        }

        @Override
        public int nextInt(int bound) {
            int choice = choices[nextChoice++];
            if (choice < 0 || choice >= bound) {
                throw new IllegalArgumentException("Test choice must be within the requested bound.");
            }
            return choice;
        }

        int getCalls() {
            return nextChoice;
        }
    }
}
