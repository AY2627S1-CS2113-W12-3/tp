package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void containsBattleshipAsGameThree() {
        Game game = new GameList().getGames().get(2);

        assertEquals("3", game.getGameId());
        assertEquals("Battleship", game.getGameName());
    }

    @Test
    void containsTicTacToeAsGameFour() {
        Game game = new GameList().getGames().get(3);

        assertEquals("4", game.getGameId());
        assertEquals("Tic Tac Toe", game.getGameName());
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

    @Test
    void gameListCannotBeChangedThroughGetter() {
        GameList gameList = new GameList();

        assertThrows(UnsupportedOperationException.class, () -> gameList.getGames().clear());
        assertEquals(5, gameList.getGames().size());
    }
}
