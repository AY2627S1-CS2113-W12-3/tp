package seedu.duke;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

/**
 * Loads and saves a player's total points in a small local properties file.
 */
public class PointsStore {
    /** Property key for the player's saved total. */
    private static final String TOTAL_POINTS_KEY = "totalPoints";

    /** The file that stores the player's points. */
    private final Path filePath;

    /**
     * Creates a store in the user's home directory.
     */
    public PointsStore() {
        this(Path.of(System.getProperty("user.home"), ".playstation-minus-one", "points.properties"));
    }

    /**
     * Creates a store at the given path.
     *
     * <p>The path constructor lets tests use a temporary location instead of the
     * player's real profile file.
     *
     * @param filePath the file used to save the total
     */
    public PointsStore(Path filePath) {
        this.filePath = Objects.requireNonNull(filePath);
    }

    /**
     * Returns the saved total, or zero if the profile file has not been created.
     *
     * @return the player's total points
     * @throws IOException if the file cannot be read or contains invalid data
     */
    public long getTotalPoints() throws IOException {
        if (!Files.exists(filePath)) {
            return 0;
        }

        Properties properties = new Properties();
        try (Reader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            properties.load(reader);
        }

        String savedTotal = properties.getProperty(TOTAL_POINTS_KEY);
        if (savedTotal == null) {
            return 0;
        }

        try {
            long totalPoints = Long.parseLong(savedTotal);
            if (totalPoints < 0) {
                throw new IOException("Saved points cannot be negative.");
            }
            return totalPoints;
        } catch (NumberFormatException e) {
            throw new IOException("Saved points are not a whole number.", e);
        }
    }

    /**
     * Adds points to the saved total and writes the result immediately.
     *
     * @param points points to add; must not be negative
     * @return the updated total
     * @throws IOException if the file cannot be read or written
     * @throws IllegalArgumentException if {@code points} is negative
     * @throws ArithmeticException if the total would overflow a {@code long}
     */
    public long addPoints(int points) throws IOException {
        if (points < 0) {
            throw new IllegalArgumentException("Points to add cannot be negative.");
        }

        long currentTotal = getTotalPoints();
        if (points == 0) {
            return currentTotal;
        }

        long updatedTotal = Math.addExact(currentTotal, points);
        saveTotalPoints(updatedTotal);
        return updatedTotal;
    }

    /** Writes a validated total, creating its parent directory when needed. */
    private void saveTotalPoints(long totalPoints) throws IOException {
        Properties properties = new Properties();
        properties.setProperty(TOTAL_POINTS_KEY, String.valueOf(totalPoints));

        Path parent = filePath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (Writer writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            properties.store(writer, "PlayStation -1 points");
        }
    }
}
