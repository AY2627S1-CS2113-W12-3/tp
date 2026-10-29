package seedu.duke;

/**
 * Represents a single node in a choose-your-own-adventure story graph.
 *
 * <p>Each node holds the scenario text shown to the player, along with
 * the available choices (if any). Ending nodes have no choices and signal
 * that the story has reached a conclusion.
 */
public class StoryNode {
    /** The scenario description displayed to the player. */
    private final String text;
    /** Human-readable labels for each choice (e.g. "Call the police"). */
    private final String[] choiceLabels;
    /** Map keys that identify the next StoryNode for each choice. */
    private final String[] choiceKeys;
    /** True if this node is a terminal ending with no further choices. */
    private final boolean isEnding;

    /**
     * Creates a story node with the given text, choices, and ending status.
     *
     * @param text         the scenario text shown to the player
     * @param choiceLabels the display labels for each choice
     * @param choiceKeys   the story-map keys for the next node each choice leads to
     * @param isEnding     true if this node is a terminal ending
     */
    public StoryNode(String text, String[] choiceLabels, String[] choiceKeys,
                     boolean isEnding) {
        this.text = text;
        this.choiceLabels = choiceLabels.clone();
        this.choiceKeys = choiceKeys.clone();
        this.isEnding = isEnding;
    }

    /**
     * Returns the scenario text for this node.
     *
     * @return the story text
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the human-readable labels for the available choices.
     *
     * @return the choice labels, or an empty array for ending nodes
     */
    public String[] getChoiceLabels() {
        return choiceLabels;
    }

    /**
     * Returns the map keys identifying the next node for each choice.
     *
     * @return the choice keys, or an empty array for ending nodes
     */
    public String[] getChoiceKeys() {
        return choiceKeys;
    }

    /**
     * Checks whether this node is a terminal ending.
     *
     * @return true if this is an ending node
     */
    public boolean isEnding() {
        return isEnding;
    }

    /**
     * Formats this node for display to the player.
     *
     * <p>Branching nodes show the scenario text followed by numbered choices.
     * Ending nodes show only the scenario text.
     *
     * @return the formatted string ready to print
     */
    public String formatForDisplay() {
        StringBuilder sb = new StringBuilder(text);
        if (!isEnding) {
            String lineSep = System.lineSeparator();
            sb.append(lineSep);
            for (int i = 0; i < choiceLabels.length; i++) {
                sb.append(lineSep).append("  ")
                        .append(i + 1).append(". ")
                        .append(choiceLabels[i]);
            }
        }
        return sb.toString();
    }
}
