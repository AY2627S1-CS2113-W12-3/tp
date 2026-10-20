package seedu.duke.battleship;

/**
 * Provides Wordle validation messages for invalid player input.
 *
 * <p>The methods live here rather than in {@link BattleshipGame} so that validation
 * messages remain consistent if other Wordle features need them later.
 */
public final class BattleshipException {

    /** Prevents this utility class from being instantiated. */
    private BattleshipException() {
    }

    public static String invalidCoordinate() {
        return "Please enter a valid coordinate from A-G and 1-7";
    }

    public static String repeatedCoordinate() {
        return "You have already hit that cell. Try a different coordinate.";
    }
}
