package seedu.duke.games.wordle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

/** Tests that the bundled Wordle word list loads correctly. */
class WordListReaderTest {

    @Test
    void loadWords_returnsNonEmptyList() {
        List<String> words = WordListReader.loadWords();
        assertFalse(words.isEmpty());
    }

    @Test
    void loadWords_allWordsAreUppercaseAndFiveLetters() {
        List<String> words = WordListReader.loadWords();
        for (String word : words) {
            assertTrue(word.matches("[A-Z]{5}"),
                    "Expected five uppercase letters but got: " + word);
        }
    }

    @Test
    void loadWords_containsNoBlankEntries() {
        List<String> words = WordListReader.loadWords();
        for (String word : words) {
            assertFalse(word.isBlank(), "Word list should not contain blank entries");
        }
    }
}
