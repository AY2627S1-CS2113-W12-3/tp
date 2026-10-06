package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the parsing of command names and arguments.
 */
class ParserTest {

    @Test
    public void parseBlankInputReturnsEmptyCommandAndArguments() {
        assertArrayEquals(new String[]{"", ""}, Parser.parse("   "));
    }

    @Test
    public void parseUppercaseCommandConvertsToLowercase() {
        assertArrayEquals(new String[]{"help", ""}, Parser.parse("HELP"));
    }

    @Test
    public void parseCommandWithArgumentsPreservesArguments() {
        assertArrayEquals(
                new String[]{"todo", "n/task d/tomorrow"},
                Parser.parse("todo n/task d/tomorrow"));
    }

    @Test
    public void parseExtraSpacesAreIgnored() {
        assertArrayEquals(new String[]{"play", "2048"}, Parser.parse("  play    2048  "));
    }
}
