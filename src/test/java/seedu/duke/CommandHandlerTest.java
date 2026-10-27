package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;

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
        commandHandler.processInput("list");

        assertTrue(output.toString().contains("[ID: 1] Wordle"));
        assertTrue(output.toString().contains("[ID: 2] Calculator"));
        assertTrue(output.toString().contains("Guess the hidden five-letter word in six attempts"));
        assertTrue(output.toString().contains("Solve randomly generated math equations"));
    }

    @Test
    void gameInputIsSentToTheActiveGame() {
        TestGame testGame = new TestGame();
        GameList gameList = new GameList();
        gameList.getGames().clear();
        gameList.getGames().add(testGame);
        commandHandler = createHandler(gameList);

        commandHandler.processInput("play test");
        commandHandler.processInput("hello");

        assertTrue(output.toString().contains("Received: hello"));
    }

    @Test
    void finishedGameReturnsToTheMenu() {
        TestGame testGame = new TestGame();
        GameList gameList = new GameList();
        gameList.getGames().clear();
        gameList.getGames().add(testGame);
        commandHandler = createHandler(gameList);

        commandHandler.processInput("play test");
        commandHandler.processInput("finish");
        commandHandler.processInput("hello");

        assertTrue(output.toString().contains("Received: finish"));
        assertTrue(output.toString().contains("Unknown command: hello"));
    }

    @Test
    void switchingGamesShowsThePreviousSessionSummary() {
        commandHandler.processInput("play 2");
        commandHandler.processInput("play 1");

        String printed = output.toString();
        assertTrue(printed.contains("Session summary"));
        assertTrue(printed.contains("Accuracy         : -"));
        assertTrue(printed.contains("Starting Wordle..."));
    }

    @Test
    void exitStopsTheApplication() {
        assertFalse(commandHandler.processInput("exit"));
        assertTrue(output.toString().contains("Bye!"));
    }

    @Test
    void scoreCommandShowsSavedPointsAtMenuAndInsideAGame() throws IOException {
        pointsStore.addPoints(7);
        TestGame testGame = new TestGame();
        commandHandler = createHandler(gameListWith(testGame));

        commandHandler.processInput("score");
        commandHandler.processInput("play test");
        commandHandler.processInput("score");

        assertEquals(2, output.toString().split("Total points: 7", -1).length - 1);
    }

    /** A small game that makes it easy to check whether input was forwarded. */
    private static class TestGame implements Game {
        private boolean finished;

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
            if (input.equals("finish")) {
                finished = true;
            }
            return "Received: " + input;
        }

        @Override
        public boolean isFinished() {
            return finished;
        }

        @Override
        public void reset() {
            finished = false;
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
        GameList gameList = new GameList();
        gameList.getGames().clear();
        gameList.getGames().add(game);
        return gameList;
    }
}
