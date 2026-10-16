package seedu.duke;

/**
 * A mental-arithmetic drill. Each round presents one randomly generated problem,
 * the player types the answer, and feedback is immediate.
 *
 * <p>One call to {@link #handleInput(String)} handles exactly one line of input and
 * returns exactly one response. The method never loops and never reads input itself,
 * so all state that has to survive between rounds -- the current problem, the score,
 * the round number -- is held in fields. Keeping the game passive like this is what
 * lets the application stay at a single command prompt instead of handing the
 * terminal over to the game.
 */
public class CalculatorGame implements Game {
    /** Identifies this game in the application. */
    private final String gameId;

    private final String gameName = "Calculator";

    /** Set once the player leaves the session, which ends the game. */
    private boolean finished = false;

    /**
     * Creates a calculator game with the given ID.
     *
     * @param gameId the ID used to select this game
     */
    public CalculatorGame(String gameId) {
        this.gameId = gameId;
    }

    @Override
    public String getGameId() {
        return gameId;
    }

    @Override
    public String getGameName() {
        return gameName;
    }

    @Override
    public String handleInput(String input) {
        return "The calculator game is not implemented yet.";
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
