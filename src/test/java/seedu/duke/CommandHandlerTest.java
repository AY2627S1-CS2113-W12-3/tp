package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Tests command routing and game-session handling. */
class CommandHandlerTest {
    @TempDir
    private Path temporaryDirectory;

    /** The real console, saved so it can be restored after each test. */
    private PrintStream originalOut;
    /** Captures output printed by the UI. */
    private ByteArrayOutputStream output;
    /** Handler used by tests that don't need a custom game list. */
    private CommandHandler commandHandler;
    /** Profile store isolated to this test class's temporary directory. */
    private PointsStore pointsStore;

    @BeforeEach
    void captureOutput() {
        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        pointsStore = new PointsStore(temporaryDirectory.resolve("points.properties"));
        commandHandler = createHandler(new GameList());
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    @Test
    void listShowsAvailableGames() {
        commandHandler.shouldContinueAfterInput("list");

        assertTrue(output.toString().contains("[ID: 1] Wordle"));
        assertTrue(output.toString().contains("[ID: 2] Calculator"));
        assertTrue(output.toString().contains("Guess the hidden five-letter word in five attempts"));
        assertTrue(output.toString().contains("Solve randomly generated math equations"));
    }

    @Test
    void gameInputIsSentToTheActiveGame() {
        TestGame testGame = new TestGame();
        GameList gameList = gameListWith(testGame);
        commandHandler = createHandler(gameList);

        commandHandler.shouldContinueAfterInput("play test");
        commandHandler.shouldContinueAfterInput("hello");

        assertTrue(output.toString().contains("Received: hello"));
    }

    @Test
    void finishedGameReturnsToTheMenu() {
        TestGame testGame = new TestGame();
        GameList gameList = gameListWith(testGame);
        commandHandler = createHandler(gameList);

        commandHandler.shouldContinueAfterInput("play test");
        commandHandler.shouldContinueAfterInput("finish");
        commandHandler.shouldContinueAfterInput("hello");

        assertTrue(output.toString().contains("Received: finish"));
        assertTrue(output.toString().contains("Unknown command: hello"));
    }

    @Test
    void switchingGamesShowsThePreviousSessionSummary() {
        commandHandler.shouldContinueAfterInput("play 2");
        commandHandler.shouldContinueAfterInput("play 1");

        String printed = output.toString();
        assertTrue(printed.contains("Session summary"));
        assertTrue(printed.contains("Accuracy         : -"));
        assertTrue(printed.contains("Starting Wordle..."));
    }

    @Test
    void exitStopsTheApplication() {
        assertFalse(commandHandler.shouldContinueAfterInput("exit"));
        assertTrue(output.toString().contains("Bye!"));
    }

    @Test
    void scoreCommandShowsSavedPointsAtMenuAndInsideAGame() throws IOException {
        pointsStore.addPoints(7);
        TestGame testGame = new TestGame();
        commandHandler = createHandler(gameListWith(testGame));

        commandHandler.shouldContinueAfterInput("score");
        commandHandler.shouldContinueAfterInput("play test");
        commandHandler.shouldContinueAfterInput("score");

        assertEquals(2, output.toString().split("Total points: 7", -1).length - 1);
    }

    @Test
    void gameActionPointsAreSavedOnceWithoutReadingTheResponse() throws IOException {
        TestGame testGame = new TestGame();
        commandHandler = createHandler(gameListWith(testGame));

        commandHandler.shouldContinueAfterInput("play test");
        commandHandler.shouldContinueAfterInput("award");
        commandHandler.shouldContinueAfterInput("score");
        commandHandler.shouldContinueAfterInput("score");

        assertEquals(3, pointsStore.getTotalPoints());
        assertTrue(output.toString().contains("You earned 3 points. Total points: 3."));
    }

    /** A small game that makes it easy to check whether input was forwarded. */
    private static class TestGame implements Game {
        private boolean isFinished;
        private int pointsEarnedForLastAction;

        @Override
        public String getGameId() {
            return "test";
        }

        @Override
        public String getGameName() {
            return "Test";
        }

        @Override
        public String handleInput(String input) {
            pointsEarnedForLastAction = 0;
            if (input.equals("finish")) {
                isFinished = true;
            } else if (input.equals("award")) {
                pointsEarnedForLastAction = 3;
            }
            return "Received: " + input;
        }

        @Override
        public int getPointsEarnedForLastAction() {
            return pointsEarnedForLastAction;
        }

        @Override
        public boolean isFinished() {
            return isFinished;
        }

        @Override
        public void reset() {
            isFinished = false;
            pointsEarnedForLastAction = 0;
        }

        @Override
        public String getDescription() {
            return "description";
        }
    }

    /** Creates a handler wired to the test profile. */
    private CommandHandler createHandler(GameList gameList) {
        return new CommandHandler(new Ui(), gameList, pointsStore);
    }

    /** Creates a game list containing only the supplied test game. */
    private static GameList gameListWith(Game game) {
        return new GameList(List.of(game));
    }
}
