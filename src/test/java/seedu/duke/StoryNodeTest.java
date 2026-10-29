package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StoryNodeTest {

    @Test
    void gettersReturnConstructorValues() {
        StoryNode node = new StoryNode("Some text",
                new String[]{"Option A", "Option B"},
                new String[]{"key_a", "key_b"},
                false);

        assertEquals("Some text", node.getText());
        assertArrayEquals(new String[]{"Option A", "Option B"},
                node.getChoiceLabels());
        assertArrayEquals(new String[]{"key_a", "key_b"},
                node.getChoiceKeys());
        assertFalse(node.isEnding());
    }

    @Test
    void constructorCopiesChoiceArrays() {
        String[] labels = {"Option A", "Option B"};
        String[] keys = {"key_a", "key_b"};
        StoryNode node = new StoryNode("Some text", labels, keys, false);

        labels[0] = "Changed option";
        keys[0] = "changed_key";

        assertArrayEquals(new String[]{"Option A", "Option B"}, node.getChoiceLabels());
        assertArrayEquals(new String[]{"key_a", "key_b"}, node.getChoiceKeys());
    }

    @Test
    void gettersReturnCopiesOfChoiceArrays() {
        StoryNode node = new StoryNode("Some text",
                new String[]{"Option A", "Option B"},
                new String[]{"key_a", "key_b"}, false);

        String[] labels = node.getChoiceLabels();
        String[] keys = node.getChoiceKeys();
        labels[0] = "Changed option";
        keys[0] = "changed_key";

        assertArrayEquals(new String[]{"Option A", "Option B"}, node.getChoiceLabels());
        assertArrayEquals(new String[]{"key_a", "key_b"}, node.getChoiceKeys());
    }

    @Test
    void endingNodeHasEmptyChoicesAndIsMarkedEnding() {
        StoryNode node = new StoryNode("The end.",
                new String[]{}, new String[]{}, true);

        assertEquals(0, node.getChoiceLabels().length);
        assertEquals(0, node.getChoiceKeys().length);
        assertTrue(node.isEnding());
    }

    @Test
    void formatForDisplayShowsNumberedChoices() {
        StoryNode node = new StoryNode("What do you do?",
                new String[]{"Run", "Hide"},
                new String[]{"run", "hide"},
                false);

        String display = node.formatForDisplay();
        assertTrue(display.contains("What do you do?"));
        assertTrue(display.contains("1. Run"));
        assertTrue(display.contains("2. Hide"));
    }

    @Test
    void formatForDisplayEndingShowsOnlyText() {
        StoryNode node = new StoryNode("You win!",
                new String[]{}, new String[]{}, true);

        assertEquals("You win!", node.formatForDisplay());
    }
}
