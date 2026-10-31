package seedu.duke;

import java.util.HashMap;
import java.util.Map;

/**
 * A choose-your-own-adventure game where the player navigates a branching
 * story about a stolen car by picking numbered choices.
 */
public class GtaGame implements Game {
    /** Command that exits the game and returns to the main menu. */
    private static final String EXIT_GAME_COMMAND = "quit";

    /** Identifies this game in the application. */
    private final String gameId;
    private final String gameName = "GTA -6";
    /** The full story graph, keyed by node identifiers. */
    private final Map<String, StoryNode> storyMap;
    /** Key of the story node the player is currently on. */
    private String currentNodeKey;
    /** Tracks whether the current session has ended. */
    private boolean isFinished;

    /**
     * Creates a new GTA -6 game with the given ID and initialises the story.
     *
     * @param gameId the ID used to select this game
     */
    public GtaGame(String gameId) {
        this.gameId = gameId;
        this.storyMap = initializeStory();
        this.currentNodeKey = "start";
        this.isFinished = false;
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
     * Returns a short description of this game.
     *
     * @return a one-line description
     */
    @Override
    public String getDescription() {
        return "A choose-your-own-adventure game about a stolen car.";
    }

    /**
     * Returns the opening scenario so the player sees the first scene and
     * choices before typing anything.
     *
     * @return the formatted opening scene
     */
    @Override
    public String getStartMessage() {
        return storyMap.get(currentNodeKey).formatForDisplay();
    }

    /**
     * Processes one line of player input: either the quit command or
     * a numbered choice that advances the story.
     *
     * @param input the text entered by the player
     * @return the next scene, an error message, or the exit confirmation
     */
    @Override
    public String handleInput(String input) {
        if (input.equalsIgnoreCase(EXIT_GAME_COMMAND)) {
            isFinished = true;
            return "Returning to the main menu.";
        }

        StoryNode currentNode = storyMap.get(currentNodeKey);
        String lineSep = System.lineSeparator();

        int choice;
        try {
            choice = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return "Please enter a valid choice number."
                    + lineSep + currentNode.formatForDisplay();
        }

        int maxChoice = currentNode.getChoiceKeys().length;
        if (choice < 1 || choice > maxChoice) {
            return "Pick a number between 1 and " + maxChoice + "."
                    + lineSep + currentNode.formatForDisplay();
        }

        String nextKey = currentNode.getChoiceKeys()[choice - 1];
        currentNodeKey = nextKey;
        StoryNode nextNode = storyMap.get(nextKey);

        if (nextNode.isEnding()) {
            isFinished = true;
        }

        return nextNode.formatForDisplay();
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void reset() {
        currentNodeKey = "start";
        isFinished = false;
    }

    /**
     * Builds the full story graph with all branching paths and endings.
     *
     * @return the story map keyed by node identifiers
     */
    private Map<String, StoryNode> initializeStory() {
        Map<String, StoryNode> map = new HashMap<>();
        addOpeningNode(map);
        addPolicePath(map);
        addWaitAtHomePath(map);
        addInvestigationPath(map);

        return map;
    }

    /** Adds the waiting-at-home path and its branches. */
    private void addWaitAtHomePath(Map<String, StoryNode> map) {
        map.put("wait_home", new StoryNode(
                "You wait at home. Hours pass. You get incredibly bored.",
                new String[]{"Go for a walk to clear your head", "Check the neighborhood chat group"},
                new String[]{"walk", "chat_group"},
                false));

        map.put("walk", new StoryNode(
                "While walking, you spot your car parked outside"
                        + " the local bakery with a giant bow on it!"
                        + System.lineSeparator() + System.lineSeparator()
                        + "ENDING: Your family got it detailed as a gift."
                        + " Looks brand new!",
                new String[]{}, new String[]{}, true));

        map.put("chat_group", new StoryNode(
                "The chat group is blowing up. Apparently a raccoon"
                        + " was seen driving a car that looks just like yours.",
                new String[]{"Go hunt for the raccoon", "Assume it's a joke and go back to sleep"},
                new String[]{"raccoon_chase", "sleep_ending"},
                false));

        map.put("raccoon_chase", new StoryNode(
                "You find the raccoon... driving your car through"
                        + " a drive-through, ordering chicken nuggets."
                        + " It crashes into a mailbox."
                        + System.lineSeparator() + System.lineSeparator()
                        + "ENDING: No nuggets were harmed, but your car"
                        + " is totaled. Insurance doesn't cover raccoons.",
                new String[]{}, new String[]{}, true));

        map.put("sleep_ending", new StoryNode(
                "You go back to sleep. When you wake up, your car"
                        + " is back in the driveway with a full tank of gas"
                        + " and a tiny thank-you note from a raccoon."
                        + System.lineSeparator() + System.lineSeparator()
                        + "ENDING: Sometimes doing nothing is the best"
                        + " strategy. Free gas!",
                new String[]{}, new String[]{}, true));
    }

    /** Adds the opening scene and its two choices to the story graph. */
    private void addOpeningNode(Map<String, StoryNode> map) {
        map.put("start", new StoryNode(
                "You wake up, stretch, and look out the window..."
                        + " YOUR CAR IS GONE!"
                        + System.lineSeparator()
                        + "What do you do?",
                new String[]{"Call the police", "Go outside to investigate"},
                new String[]{"police", "investigate"},
                false));
    }

    /** Adds the police-report path and its immediate ending. */
    private void addPolicePath(Map<String, StoryNode> map) {
        map.put("police", new StoryNode(
                "You call 911. The operator asks if you"
                        + " saw anything suspicious last night.",
                new String[]{"Mention the suspicious ice-cream truck", "Say you saw nothing"},
                new String[]{"icecream_lead", "wait_home"},
                false));

        map.put("icecream_lead", new StoryNode(
                "The police track down the ice-cream truck."
                        + System.lineSeparator()
                        + "It was your neighbor Bob! He 'borrowed' your car"
                        + " to set up a surprise birthday party for you."
                        + System.lineSeparator() + System.lineSeparator()
                        + "ENDING: You get cake AND your car back. Happy birthday!",
                new String[]{}, new String[]{}, true));
    }

    /** Adds the investigation path and its endings. */
    private void addInvestigationPath(Map<String, StoryNode> map) {
        map.put("investigate", new StoryNode(
                "You go outside. There are tire marks and..."
                        + " a trail of cheese puffs leading down the street.",
                new String[]{"Follow the cheese puff trail", "Ask your neighbor if they saw anything"},
                new String[]{"cheese_trail", "neighbor"},
                false));

        map.put("cheese_trail", new StoryNode(
                "The trail leads to the park, where you find your car"
                        + " covered in cheese puff dust. A family of raccoons"
                        + " is living in the back seat.",
                new String[]{"Shoo the raccoons away politely", "Offer the raccoons a ride home"},
                new String[]{"raccoon_polite", "raccoon_ride"},
                false));

        map.put("raccoon_polite", new StoryNode(
                "The raccoons leave peacefully, but your car now"
                        + " permanently smells like cheese puffs."
                        + System.lineSeparator() + System.lineSeparator()
                        + "ENDING: You got your car back. Could be worse."
                        + " At least it smells delicious.",
                new String[]{}, new String[]{}, true));

        map.put("raccoon_ride", new StoryNode(
                "You drive the raccoons to the forest. They wave goodbye."
                        + " One leaves you a tiny hand-written thank-you note."
                        + System.lineSeparator() + System.lineSeparator()
                        + "ENDING: You made friends with raccoons AND"
                        + " got your car back. Best day ever.",
                new String[]{}, new String[]{}, true));

        map.put("neighbor", new StoryNode(
                "Your neighbor says they saw someone in a banana costume"
                        + " drive off in your car at 3 AM. The police never"
                        + " find banana-costume guy."
                        + System.lineSeparator() + System.lineSeparator()
                        + "ENDING: You get the insurance money and buy a"
                        + " bicycle. Healthier, honestly.",
                new String[]{}, new String[]{}, true));
    }
}
