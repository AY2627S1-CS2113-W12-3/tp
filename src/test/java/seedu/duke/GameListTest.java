package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/** Tests which games are registered and that each one can be told apart by ID. */
class GameListTest {

    @Test
    void containsWordleAsGameOne() {
        Game game = new GameList().getGames().get(0);

        assertEquals("1", game.getGameId());
        assertEquals("Wordle", game.getGameName());
    }

    @Test
    void containsCalculatorAsGameTwo() {
        Game game = new GameList().getGames().get(1);

        assertEquals("2", game.getGameId());
        assertEquals("Calculator", game.getGameName());
    }

    @Test
    void gameIdsAreUnique() {
        GameList gameList = new GameList();

        // A Set drops duplicates, so it only matches the list size if every ID is different.
        Set<String> ids = new HashSet<>();
        for (Game game : gameList.getGames()) {
            ids.add(game.getGameId());
        }

        assertEquals(gameList.getGames().size(), ids.size());
    }
}
