package seedu.duke;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

/**
 * Provides a Wordle-style game with up to five guesses per session.
 */
public class WordleGame implements Game {
    /** Records diagnostic events without changing the user-facing output. */
    private static final Logger LOGGER = Logger.getLogger(WordleGame.class.getName());
    /** The number of incorrect guesses allowed before a session ends. */
    private static final int MAX_ATTEMPTS = 5;
    /** The text file, bundled with the app, that contains one Wordle answer per line. */
    private static final String WORD_LIST_RESOURCE = "/wordle-words.txt";
    /** The allowed answers loaded from {@value #WORD_LIST_RESOURCE}. */
    private static final List<String> SECRET_WORDS = loadSecretWords();
    /** Command used to leave a Wordle session and return to the main menu. */
    private static final String QUIT_COMMAND = "quit";
    /**
     * Identifies this game in the application.
     */
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
     * Checks a submitted word or processes the {@code quit} command. A correct
     * word ends the game immediately; otherwise, the game ends after five incorrect guesses.
     * Whenever the game ends, the response says so, because the application
     * returns to the main menu straight afterwards.
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

        if (!guess.matches("[a-zA-Z]{5}")) {
            return WordleException.invalidWord();
        }

        String normalizedGuess = guess.toUpperCase(Locale.ROOT);
        if (!guessedWords.add(normalizedGuess)) {
            return WordleException.repeatedWord();
        }

        if (guess.equalsIgnoreCase(secretWord)) {
            finished = true;
            // attemptsUsed only counts wrong guesses, so add this correct one.
            int triesTaken = attemptsUsed + 1;
            LOGGER.info("Player won Wordle in " + formatTries(triesTaken) + ".");
            return correctGuess + " You got it in " + formatTries(triesTaken) + "."
                    + System.lineSeparator() + exitGameMessage;
        }

        attemptsUsed++;
        // The game finishes on the last allowed guess, so it is never asked to grade another.
        assert attemptsUsed <= MAX_ATTEMPTS : "Wrong guesses cannot exceed the limit";
        int triesLeft = MAX_ATTEMPTS - attemptsUsed;
        if (triesLeft == 0) {
            LOGGER.info("Player ran out of tries in Wordle.");
            finished = true;
            return wrongGuess + " You are out of tries."
                    + System.lineSeparator() + exitGameMessage;
        }
        // FINE is below the default INFO level, so per-guess detail stays off the console.
        LOGGER.fine("Wrong guess: " + input + ", " + formatTries(triesLeft) + " left.");
        return wrongGuess + " You have " + formatTries(triesLeft) + " left.";
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
     * Loads the Wordle answers stored one per line in the bundled text file.
     *
     * @return the non-empty words in the text file, normalized to uppercase
     * @throws IllegalStateException if the text file cannot be read or has no words
     */
    private static List<String> loadSecretWords() {
        InputStream input = WordleGame.class.getResourceAsStream(WORD_LIST_RESOURCE);
        if (input == null) {
            throw new IllegalStateException("Wordle word list could not be found.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            List<String> words = reader.lines()
                    .map(String::trim)
                    .filter(word -> !word.isEmpty())
                    .map(word -> word.toUpperCase(Locale.ROOT))
                    .toList();
            if (words.isEmpty()) {
                throw new IllegalStateException("Wordle word list must contain at least one word.");
            }
            return words;
        } catch (IOException e) {
            throw new IllegalStateException("Wordle word list could not be read.", e);
        }
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
     * Changes the status of completion back to uncompleted so that replay is possible
     */
    @Override
    public void reset() {
        finished = false;
        attemptsUsed = 0;
        guessedWords.clear();
        secretWord = chooseSecretWord();
    }
}
