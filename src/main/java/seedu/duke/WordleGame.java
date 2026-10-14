package seedu.duke;

import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * A five-letter word guessing game with a limited built-in vocabulary.
 */
public class WordleGame implements Game {
    /** ANSI reset code used after each coloured letter. */
    private static final String ANSI_RESET = "\u001B[0m";
    /** ANSI colour code for a letter that is not in the answer. */
    private static final String ANSI_RED = "\u001B[31m";
    /** ANSI colour code for a letter that occurs in the answer but is misplaced. */
    private static final String ANSI_YELLOW = "\u001B[33m";
    /** ANSI colour code for a letter in the correct position. */
    private static final String ANSI_GREEN = "\u001B[32m";
    /** Number of letters in every word used by this game. */
    private static final int WORD_LENGTH = 5;
    /** Number of valid guesses the player may make. */
    private static final int MAX_ATTEMPTS = 5;
    /** The hidden words that may be selected as answers. */
    private static final List<String> WORDS = List.of("apple", "berry", "crane", "grape", "mango");
    /** Random source used when selecting an answer for a new game. */
    private static final Random RANDOM = new Random();

    private final String answer;
    private int attemptsUsed;
    private boolean finished;

    /**
     * Creates a game with a randomly selected answer from the built-in vocabulary.
     */
    public WordleGame() {
        this(WORDS.get(RANDOM.nextInt(WORDS.size())));
    }

    /**
     * Creates a game with a specific answer. This constructor supports repeatable tests.
     *
     * @param answer the answer to guess; it must be in the built-in vocabulary
     * @throws IllegalArgumentException if the answer is not an allowed Wordle word
     */
    WordleGame(String answer) {
        if (!WORDS.contains(answer)) {
            throw new IllegalArgumentException("Wordle answer must be in the built-in vocabulary");
        }
        this.answer = answer;
    }

    /**
     * Processes one guess and returns coloured feedback or a validation message.
     *
     * @param input the player's guess
     * @return feedback for the guess, including the game's outcome when it has ended
     */
    @Override
    public String handleInput(String input) {
        if (finished) {
            throw new IllegalStateException("This Wordle game has already finished");
        }
        if (input == null) {
            throw new IllegalArgumentException("A Wordle guess cannot be null");
        }

        String guess = input.trim().toLowerCase(Locale.ROOT);
        if (!isFiveLetterWord(guess)) {
            return "Enter a five-letter word.";
        }

        attemptsUsed++;
        String feedback = formatFeedback(guess);
        if (guess.equals(answer)) {
            finished = true;
            return feedback + "\nYou solved it in " + attemptsUsed + " attempt(s)!";
        }
        if (attemptsUsed == MAX_ATTEMPTS) {
            finished = true;
            return feedback + "\nNo attempts left. The word was " + answer.toUpperCase(Locale.ROOT) + ".";
        }
        return feedback + "\n" + (MAX_ATTEMPTS - attemptsUsed) + " attempt(s) remaining.";
    }

    /**
     * Returns whether the player has won or used all permitted guesses.
     *
     * @return true when this game should return control to the launcher
     */
    @Override
    public boolean isFinished() {
        return finished;
    }

    /**
     * Checks that a guess has exactly five English alphabetic characters.
     *
     * @param guess the normalised player guess
     * @return true if the guess can be scored by this game
     */
    private boolean isFiveLetterWord(String guess) {
        if (guess.length() != WORD_LENGTH) {
            return false;
        }
        for (int index = 0; index < WORD_LENGTH; index++) {
            char letter = guess.charAt(index);
            if (letter < 'a' || letter > 'z') {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates Wordle-style coloured feedback while accounting for repeated letters.
     *
     * @param guess a five-letter English alphabetic guess
     * @return the guess with each letter coloured according to its match state
     */
    private String formatFeedback(String guess) {
        assert guess.length() == WORD_LENGTH : "A valid Wordle guess must have five letters";
        int[] unmatchedAnswerLetters = new int[26];
        char[] states = new char[WORD_LENGTH];

        for (int index = 0; index < WORD_LENGTH; index++) {
            if (guess.charAt(index) == answer.charAt(index)) {
                states[index] = 'G';
            } else {
                unmatchedAnswerLetters[answer.charAt(index) - 'a']++;
            }
        }
        for (int index = 0; index < WORD_LENGTH; index++) {
            if (states[index] == 'G') {
                continue;
            }
            int letterIndex = guess.charAt(index) - 'a';
            if (unmatchedAnswerLetters[letterIndex] > 0) {
                states[index] = 'Y';
                unmatchedAnswerLetters[letterIndex]--;
            } else {
                states[index] = 'R';
            }
        }

        StringBuilder feedback = new StringBuilder();
        for (int index = 0; index < WORD_LENGTH; index++) {
            feedback.append(colourFor(states[index]));
            feedback.append(Character.toUpperCase(guess.charAt(index)));
            feedback.append(ANSI_RESET);
        }
        return feedback.toString();
    }

    /**
     * Returns the ANSI colour associated with a Wordle feedback state.
     *
     * @param state the feedback state: green, yellow, or red
     * @return an ANSI escape sequence for the requested colour
     */
    private String colourFor(char state) {
        return switch (state) {
        case 'G' -> ANSI_GREEN;
        case 'Y' -> ANSI_YELLOW;
        default -> ANSI_RED;
        };
    }
}
