package seedu.duke.battleship;

/**
 * Provides Battleship error messages for invalid player input.
 *
 * <p>The methods live here rather than in {@link BattleshipGame} so that validation
 * messages remain consistent if other Battleship features need them later.
 */
public final class BattleshipErrorMessages {

    /** Prevents this utility class from being instantiated. */
    private BattleshipErrorMessages() {
    }

    /**
     * Returns the message for an invalid attack coordinate.
     *
     * @return the invalid-coordinate message
     */
    public static String invalidCoordinate() {
        return "Please enter a valid coordinate from A-G and 1-7";
    }

    /**
     * Returns the message when a player attacks an already attacked cell.
     *
     * @return the repeated-coordinate message
     */
    public static String repeatedCoordinate() {
        return "You have already hit that cell. Try a different coordinate.";
    }
}
