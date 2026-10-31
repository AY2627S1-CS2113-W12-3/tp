package seedu.duke.games.wordle;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Evaluates Wordle guesses and keeps the letter hints discovered during a game.
 */
public final class WordleHelp {
    /** ANSI code for a correctly placed letter. */
    private static final String GREEN = "\u001B[32m";
    /** ANSI code for a letter that is in the word but in the wrong position. */
    private static final String YELLOW = "\u001B[33m";
    /** ANSI code for a letter that is not in the secret word. */
    private static final String RED = "\u001B[31m";
    /** ANSI code that returns terminal text to its default colour. */
    private static final String RESET = "\u001B[0m";

    /** Correctly positioned letters, stored with their zero-based positions. */
    private final Map<Integer, Character> correctPositionLetters = new HashMap<>();
    /** Letters known to be present but not in a correct position. */
    private final Set<Character> incorrectPositionLetters = new LinkedHashSet<>();
    /** Letters known not to occur in the secret word. */
    private final Set<Character> absentLetters = new LinkedHashSet<>();

    /**
     * Evaluates a valid guess, records its letter hints, and adds ANSI colours.
     *
     * @param guess a five-letter uppercase guess
     * @param secretWord the five-letter uppercase answer
     * @return the guess with ANSI colour codes around every letter
     */
    public String evaluateGuess(String guess, String secretWord) {
        Map<Character, Integer> unmatchedSecretLetters = new HashMap<>();
        String[] colours = new String[guess.length()];

        for (int index = 0; index < guess.length(); index++) {
            char secretLetter = secretWord.charAt(index);
            if (guess.charAt(index) == secretLetter) {
                colours[index] = GREEN;
                recordCorrectPosition(index, secretLetter);
            } else {
                unmatchedSecretLetters.merge(secretLetter, 1, Integer::sum);
            }
        }

        for (int index = 0; index < guess.length(); index++) {
            if (colours[index] != null) {
                continue;
            }

            char guessedLetter = guess.charAt(index);
            int remainingCount = unmatchedSecretLetters.getOrDefault(guessedLetter, 0);
            if (remainingCount > 0) {
                colours[index] = YELLOW;
                unmatchedSecretLetters.put(guessedLetter, remainingCount - 1);
                recordIncorrectPosition(guessedLetter);
            } else {
                colours[index] = RED;
                recordAbsentLetter(guessedLetter);
            }
        }

        StringBuilder colouredGuess = new StringBuilder();
        for (int index = 0; index < guess.length(); index++) {
            colouredGuess.append(colours[index]).append(guess.charAt(index)).append(RESET);
        }
        return colouredGuess.toString();
    }

    /**
     * Returns a colour-coded summary of the letter hints discovered so far.
     *
     * @return the discovered letter hints, or a validation message before any guess
     */
    public String listLetters() {
        if (correctPositionLetters.isEmpty() && incorrectPositionLetters.isEmpty() && absentLetters.isEmpty()) {
            return WordleException.noLettersListed();
        }

        return "Letters with correct positions:" + System.lineSeparator()
                + formatCorrectPositions() + System.lineSeparator() + System.lineSeparator()
                + "Letters in incorrect positions:" + System.lineSeparator()
                + formatLetters(incorrectPositionLetters, YELLOW) + System.lineSeparator() + System.lineSeparator()
                + "Letters not in the word:" + System.lineSeparator()
                + formatLetters(absentLetters, RED);
    }

    /** Clears all hints before a new Wordle game begins. */
    public void reset() {
        correctPositionLetters.clear();
        incorrectPositionLetters.clear();
        absentLetters.clear();
    }

    /** Records a green letter, which supersedes other hints for that letter. */
    private void recordCorrectPosition(int position, char letter) {
        correctPositionLetters.put(position, letter);
        incorrectPositionLetters.remove(letter);
        absentLetters.remove(letter);
    }

    /** Records a yellow letter unless it is already known at a correct position. */
    private void recordIncorrectPosition(char letter) {
        if (!correctPositionLetters.containsValue(letter)) {
            incorrectPositionLetters.add(letter);
        }
        absentLetters.remove(letter);
    }

    /** Records a red letter only when no earlier hint proves it is in the answer. */
    private void recordAbsentLetter(char letter) {
        if (!correctPositionLetters.containsValue(letter) && !incorrectPositionLetters.contains(letter)) {
            absentLetters.add(letter);
        }
    }

    /** Formats letters that are known to be in their correct positions. */
    private String formatCorrectPositions() {
        if (correctPositionLetters.isEmpty()) {
            return "None";
        }

        StringJoiner hints = new StringJoiner(System.lineSeparator());
        for (int position = 0; position < 5; position++) {
            Character letter = correctPositionLetters.get(position);
            if (letter != null) {
                hints.add(GREEN + letter + RESET + " (position " + (position + 1) + ")");
            }
        }
        return hints.toString();
    }

    /** Formats a coloured, comma-separated list of letters. */
    private static String formatLetters(Set<Character> letters, String colour) {
        if (letters.isEmpty()) {
            return "None";
        }

        StringJoiner formattedLetters = new StringJoiner(", ");
        for (char letter : letters) {
            formattedLetters.add(colour + letter + RESET);
        }
        return formattedLetters.toString();
    }
}
