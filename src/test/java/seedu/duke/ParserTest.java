package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

/**
 * Tests the parsing of command names and arguments.
 */
class ParserTest {

    @Test
    public void parseBlankInputReturnsEmptyCommandAndArguments() {
        assertArrayEquals(new String[]{"", ""}, Parser.parse("   "));
    }

    @Test
    public void parseNullInputReturnsEmptyCommandAndArguments() {
        assertArrayEquals(new String[]{"", ""}, Parser.parse(null));
    }

    @Test
    public void parseUppercaseCommandConvertsToLowercase() {
        assertArrayEquals(new String[]{"help", ""}, Parser.parse("HELP"));
    }

    @Test
    @ResourceLock(Resources.LOCALE)
    public void parseCommandsIndependentOfDefaultLocale() {
        Locale originalLocale = Locale.getDefault();
        try {
            Locale.setDefault(Locale.forLanguageTag("tr-TR"));
            assertArrayEquals(new String[]{"list", ""}, Parser.parse("LIST"));
            assertArrayEquals(new String[]{"exit", ""}, Parser.parse("EXIT"));
        } finally {
            Locale.setDefault(originalLocale);
        }
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
