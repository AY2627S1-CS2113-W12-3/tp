package seedu.duke.wordle;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import seedu.duke.Game;

/**
 * Provides a Wordle-style game with up to five guesses per session.
 */
public class WordleGame implements Game {
    /** Records diagnostic events without changing the user-facing output. */
    private static final Logger LOGGER = Logger.getLogger(WordleGame.class.getName());
    /** The number of incorrect guesses allowed before a session ends. */
    private static final int MAX_ATTEMPTS = 5;
    /** The allowed answers loaded from the Wordle word-list text file. */
    private static final List<String> SECRET_WORDS = WordListReader.loadWords();
    /** Command used to leave a Wordle session and return to the main menu. */
    private static final String QUIT_COMMAND = "quit";
    /** Command that displays the discovered Wordle letter hints. */
    private static final String LIST_LETTERS_COMMAND = "list letters";
    /** Identifies this game in the application. */
    private final String gameId;
    private final String gameName = "Wordle";
    private final String correctGuess = "Correct guess!";
    private final String wrongGuess = "Wrong guess!";
    private final String exitGameMessage = "Returning to the main menu.";
    /** Tracks whether the current game session has ended. */
    private boolean finished = false;
    /** Counts incorrect guesses in the current game session. */
    private int attemptsUsed = 0;
    /** Stores accepted guesses so the player cannot use the same word twice. */
    private final Set<String> guessedWords = new HashSet<>();
    /** Evaluates guesses and stores the letter hints discovered in this game. */
    private final WordleHelp wordleHelp = new WordleHelp();
    /** The word the player must guess in the current game. */
    private String secretWord;

    public WordleGame(String gameId) {
        this.gameId = gameId;
        secretWord = chooseSecretWord();
    }

    @Override
    public String getGameId() {
        return gameId;
    }

    @Override
    public String getGameName() {
        return gameName;
    }

    /**
     * Tells the player how many tries they have and how to leave, so they know
     * the rules before typing their first guess.
     *
     * @return the opening instructions
     */
    @Override
    public String getStartMessage() {
        return "Guess the word. You have " + formatTries(MAX_ATTEMPTS)
                + ". Type 'quit' to leave.";
    }

    /**
     * Checks a submitted word or processes the {@code quit} command.
     *
     * @param input the word entered by the player
     * @return feedback about the submitted word or game exit
     */
    @Override
    public String handleInput(String input) {
        String guess = input == null ? "" : input.trim();
        if (guess.equalsIgnoreCase(QUIT_COMMAND)) {
            LOGGER.info("Player quit Wordle after " + attemptsUsed + " wrong guesses.");
            finished = true;
            return exitGameMessage;
        }

        if (guess.equalsIgnoreCase(LIST_LETTERS_COMMAND)) {
            return wordleHelp.listLetters();
        }

        if (!guess.matches("[a-zA-Z]{5}")) {
            return WordleException.invalidWord();
        }

        String normalizedGuess = guess.toUpperCase(Locale.ROOT);
        if (!guessedWords.add(normalizedGuess)) {
            return WordleException.repeatedWord();
        }

        String feedback = wordleHelp.evaluateGuess(normalizedGuess, secretWord);
        if (guess.equalsIgnoreCase(secretWord)) {
            finished = true;
            int triesTaken = attemptsUsed + 1;
            LOGGER.info("Player won Wordle in " + formatTries(triesTaken) + ".");
            return feedback + System.lineSeparator()
                    + correctGuess + " You got it in " + formatTries(triesTaken) + "."
                    + System.lineSeparator() + exitGameMessage;
        }

        attemptsUsed++;
        assert attemptsUsed <= MAX_ATTEMPTS : "Wrong guesses cannot exceed the limit";
        int triesLeft = MAX_ATTEMPTS - attemptsUsed;
        if (triesLeft == 0) {
            LOGGER.info("Player ran out of tries in Wordle.");
            finished = true;
            return feedback + System.lineSeparator()
                    + wrongGuess + " You are out of tries."
                    + System.lineSeparator() + "The word was " + secretWord + "."
                    + System.lineSeparator() + exitGameMessage;
        }
        LOGGER.fine("Wrong guess: " + input + ", " + formatTries(triesLeft) + " left.");
        return feedback + System.lineSeparator()
                + wrongGuess + " You have " + formatTries(triesLeft) + " left.";
    }

    /**
     * Formats a number of tries with the correct singular or plural noun.
     *
     * @param count the number of tries
     * @return for example "1 try" or "3 tries"
     */
    private static String formatTries(int count) {
        assert count >= 0 : "Number of tries cannot be negative";
        return count + (count == 1 ? " try" : " tries");
    }

    /**
     * Chooses one secret word from the available Wordle answers.
     *
     * @return the randomly selected secret word
     */
    private static String chooseSecretWord() {
        return SECRET_WORDS.get(ThreadLocalRandom.current().nextInt(SECRET_WORDS.size()));
    }

    /**
     * Reports whether the current game has finished.
     *
     * @return true if game is finished, false otherwise
     */
    @Override
    public boolean isFinished() {
        return finished;
    }

    /**
     * Changes the status of completion back to uncompleted so that replay is possible.
     */
    @Override
    public void reset() {
        finished = false;
        attemptsUsed = 0;
        guessedWords.clear();
        wordleHelp.reset();
        secretWord = chooseSecretWord();
    }

    /**
     * Returns the commands belonging specifically to Wordle
     * and their functions
     *
     * @return formatted string containing the commands and function
     */
    @Override
    public String getCommands() {
        return "  list letters  - Show discovered Wordle letter hints" + System.lineSeparator();
    }
}
