import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SpaceObjectTest {

    @Test
    void testLoadFromRow_validData() {
        String[] row = {
            "R123", "Nolan", "USA", "LEO", "SATELLITE",
            "2020", "LaunchSite1", "45.0", "44.5", "9q9hv",
            "12345", "5"
        };

        SpaceObject obj = new SpaceObject();
        obj.loadFromRow(row, "contour-export-CS3331.csv");

        assertEquals("R123", obj.recordID);
        assertEquals("Nolan", obj.satelliteName);
        assertEquals("USA", obj.country);
        assertEquals("LEO", obj.orbitType);
        assertEquals("SATELLITE", obj.objectType);
        assertEquals(2020, obj.launchYear);
        assertEquals("LaunchSite1", obj.launchSite);
        assertEquals(45.0, obj.longitude);
        assertEquals(44.5, obj.avgLongitude);
        assertEquals("9q9hv", obj.geohash);
        assertEquals(12345, obj.daysOld);
        assertEquals(Long.valueOf(5), obj.conjuctionCount);
    }

    @Test
    void testLoadFromRow_invalidNumberFormat() {
        String[] row = {
            "R123", "Nolan", "USA", "LEO", "SATELLITE",
            "notANumber", "LaunchSite1", "45.0", "44.5", "9q9hv",
            "12345", "5"
        };

        SpaceObject obj = new SpaceObject();
        assertThrows(NumberFormatException.class, () -> obj.loadFromRow(row, "contour-export-CS3331.csv"));
    }

    @Test
    void testLoadFromRow_shortRow() {
        String[] row = {
            "R123", "Nolan", "USA"
        };

        SpaceObject obj = new SpaceObject();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> obj.loadFromRow(row, "contour-export-CS3331.csv"));
    }
}
