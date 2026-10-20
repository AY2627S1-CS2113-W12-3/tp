package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Tests command routing and game-session handling. */
class CommandHandlerTest {
    /** The real console, saved so it can be restored after each test. */
    private PrintStream originalOut;
    /** Captures output printed by the UI. */
    private ByteArrayOutputStream output;
    /** Handler used by tests that don't need a custom game list. */
    private CommandHandler commandHandler;

    @BeforeEach
    void captureOutput() {
        originalOut = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        commandHandler = new CommandHandler(new Ui(), new GameList());
    }

    @AfterEach
    void restoreOutput() {
        System.setOut(originalOut);
    }

    @Test
    void listShowsAvailableGames() {
        commandHandler.processInput("list");

        assertTrue(output.toString().contains("Wordle | ID: 1"));
        assertTrue(output.toString().contains("Calculator | ID: 2"));
    }

    @Test
    void gameInputIsSentToTheActiveGame() {
        TestGame testGame = new TestGame();
        GameList gameList = new GameList();
        gameList.getGames().clear();
        gameList.getGames().add(testGame);
        commandHandler = new CommandHandler(new Ui(), gameList);

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
        commandHandler = new CommandHandler(new Ui(), gameList);

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
    }
}
