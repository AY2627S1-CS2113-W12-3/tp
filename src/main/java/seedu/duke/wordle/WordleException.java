package seedu.duke.wordle;

/**
 * Provides Wordle validation messages for invalid player input.
 *
 * <p>The methods live here rather than in {@link WordleGame} so that validation
 * messages remain consistent if other Wordle features need them later.
 */
public final class WordleException {

    /** Prevents this utility class from being instantiated. */
    private WordleException() {
    }

    /**
     * Returns the message for a guess that is not exactly five letters.
     *
     * @return the invalid-word message
     */
    public static String invalidWord() {
        return "Please enter a five-letter word.";
    }

    /**
     * Returns the message for a word guessed earlier in the current game.
     *
     * @return the repeated-word message
     */
    public static String repeatedWord() {
        return "You have already guessed that word. Try a different five-letter word.";
    }

    /**
     * Returns the message when {@code list letters} is used outside Wordle.
     *
     * @return the command-scope message
     */
    public static String listLettersOnlyInWordle() {
        return "The 'list letters' command only works while playing Wordle.";
    }

    /**
     * Returns the message when no valid Wordle guess has supplied letter hints yet.
     *
     * @return the missing-hints message
     */
    public static String noLettersListed() {
        return "Make a valid Wordle guess before listing letters.";
    }

    // TODO: Add public static String wordNotInDictionary() when dictionary validation is implemented.
}
