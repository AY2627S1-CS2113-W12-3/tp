package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Tests loading and saving the player's persistent points. */
class PointsStoreTest {
    @TempDir
    private Path temporaryDirectory;

    @Test
    void missingProfileStartsAtZero() throws IOException {
        PointsStore store = new PointsStore(temporaryDirectory.resolve("profile/points.properties"));

        assertEquals(0, store.getTotalPoints());
    }

    @Test
    void awardedPointsSurviveLoadingTheProfileAgain() throws IOException {
        Path profilePath = temporaryDirectory.resolve("profile/points.properties");
        PointsStore store = new PointsStore(profilePath);

        assertEquals(5, store.addPoints(5));
        assertEquals(8, store.addPoints(3));
        assertEquals(8, new PointsStore(profilePath).getTotalPoints());
        assertTrue(Files.exists(profilePath));
    }

    @Test
    void negativeAwardIsRejected() {
        PointsStore store = new PointsStore(temporaryDirectory.resolve("points.properties"));

        assertThrows(IllegalArgumentException.class, () -> store.addPoints(-1));
    }

    @Test
    void invalidSavedTotalIsReported() throws IOException {
        Path profilePath = temporaryDirectory.resolve("points.properties");
        Files.writeString(profilePath, "totalPoints=not-a-number");
        PointsStore store = new PointsStore(profilePath);

        assertThrows(IOException.class, store::getTotalPoints);
    }
}
