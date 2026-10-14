package seedu.duke;

/**
 * Parses user input into a command name and its arguments.
 */
public class Parser {

    /**
     * Splits the given input into a command name and arguments.
     * The command name is the first word (lowercased), and arguments
     * are everything after it. Blank input returns two empty strings.
     *
     * @param input The raw user input string.
     * @return A String array where index 0 is the command name and index 1 is the arguments.
     */
    public static String[] parse(String input) {
        if (input == null || input.isBlank()) {
            return new String[]{"", ""};
        }
        String[] parts = input.trim().split("\\s+", 2);
        String command = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1] : "";
        String[] parsedInput = new String[]{command, arguments};
        assert parsedInput.length == 2 : "A parsed command must contain a command and arguments";
        return parsedInput;
    }
}
