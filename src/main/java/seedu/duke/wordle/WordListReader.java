package seedu.duke.wordle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

/**
 * Loads the possible Wordle answers from the bundled word-list text file.
 */
public final class WordListReader {
    /** The text file containing one Wordle answer per line. */
    private static final String WORD_LIST_RESOURCE = "/wordle/wordle-words.txt";

    /** Prevents this utility class from being instantiated. */
    private WordListReader() {
    }

    /**
     * Loads the non-empty lines in the word-list text file as uppercase words.
     *
     * @return the available Wordle answers
     * @throws IllegalStateException if the text file cannot be read or has no words
     */
    public static List<String> loadWords() {
        InputStream input = WordListReader.class.getResourceAsStream(WORD_LIST_RESOURCE);
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
}
