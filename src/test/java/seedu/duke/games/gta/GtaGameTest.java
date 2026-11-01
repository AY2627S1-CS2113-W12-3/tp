package seedu.duke.games.gta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GtaGameTest {

    private GtaGame newGame() {
        return new GtaGame("3");
    }

    @Test
    void getGameIdAndNameAreCorrect() {
        GtaGame game = newGame();
        assertEquals("3", game.getGameId());
        assertEquals("GTA -6", game.getGameName());
    }

    @Test
    void getStartMessageShowsOpeningScenario() {
        GtaGame game = newGame();
        String start = game.getStartMessage();
        assertTrue(start.contains("YOUR CAR IS GONE"));
        assertTrue(start.contains("1. Call the police"));
        assertTrue(start.contains("2. Go outside to investigate"));
    }

    @Test
    void validChoiceAdvancesToNextNode() {
        GtaGame game = newGame();
        String response = game.handleInput("1");
        assertTrue(response.contains("911"));
        assertFalse(game.isFinished());
    }

    @Test
    void reachingEndingSetsFinished() {
        GtaGame game = newGame();
        // Path: start -> police -> icecream_lead (ending)
        game.handleInput("1");
        String ending = game.handleInput("1");
        assertTrue(game.isFinished());
        assertTrue(ending.contains("ENDING"));
    }

    @Test
    void secondPathReachesEnding() {
        GtaGame game = newGame();
        // Path: start -> investigate -> neighbor (ending)
        game.handleInput("2");
        String ending = game.handleInput("2");
        assertTrue(game.isFinished());
        assertTrue(ending.contains("ENDING"));
    }

    @Test
    void everyStoryBranchCanReachAnEnding() {
        String[][] paths = {
            {"1", "1"},
            {"1", "2", "1"},
            {"1", "2", "2", "1"},
            {"1", "2", "2", "2"},
            {"2", "1", "1"},
            {"2", "1", "2"},
            {"2", "2"}
        };

        for (String[] path : paths) {
            GtaGame game = newGame();
            String response = "";
            for (String choice : path) {
                response = game.handleInput(choice);
            }

            assertTrue(game.isFinished(), "Path should finish: " + String.join(" -> ", path));
            assertTrue(response.contains("ENDING"), "Path should show an ending: "
                    + String.join(" -> ", path));
        }
    }

    @Test
    void exitGameCommandFinishesGame() {
        GtaGame game = newGame();
        String response = game.handleInput("quit");
        assertEquals("Returning to the main menu.", response);
        assertTrue(game.isFinished());
    }

    @Test
    void exitGameCommandIsCaseInsensitive() {
        GtaGame game = newGame();
        String response = game.handleInput("QUIT");
        assertEquals("Returning to the main menu.", response);
        assertTrue(game.isFinished());
    }

    @Test
    void invalidInputNonNumericReDisplaysCurrentNode() {
        GtaGame game = newGame();
        String response = game.handleInput("hello");
        assertTrue(response.contains("Please enter a valid choice number."));
        assertTrue(response.contains("1. Call the police"));
        assertFalse(game.isFinished());
    }

    @Test
    void invalidInputOutOfRangeReDisplaysCurrentNode() {
        GtaGame game = newGame();
        String response = game.handleInput("5");
        assertTrue(response.contains("Pick a number between 1 and 2."));
        assertTrue(response.contains("1. Call the police"));
        assertFalse(game.isFinished());
    }

    @Test
    void resetRestoresGameToStart() {
        GtaGame game = newGame();
        // Play to an ending
        game.handleInput("1");
        game.handleInput("1");
        assertTrue(game.isFinished());

        game.reset();
        assertFalse(game.isFinished());
        String start = game.getStartMessage();
        assertTrue(start.contains("YOUR CAR IS GONE"));
    }
}
