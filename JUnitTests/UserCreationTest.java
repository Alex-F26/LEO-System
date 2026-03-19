import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class UserCreationTest {

    private static final String TEST_CSV_FILE = "test-user-list.csv";
    private UserCreation userCreation;

    @BeforeEach
    public void setUp() throws IOException {
        // Prepare a fresh test CSV file with header and some sample data
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_CSV_FILE))) {
            writer.write("UserType,ID,Name,Username,Password\n");
            writer.write("Scientist,1001,John Doe,jdoe,password123\n");
            writer.write("Administrator,1002,Admin User,admin,password456\n");
        }
        userCreation = new UserCreation();

        // Override the outputFile path to the test CSV file (reflection or change visibility)
        // Here I assume you can add a setter or make the field public/protected for testing:
        // (if not, you can modify UserCreation to accept the file path in constructor)
        try {
            java.lang.reflect.Field field = UserCreation.class.getDeclaredField("outputFile");
            field.setAccessible(true);
            field.set(userCreation, TEST_CSV_FILE);
        } catch (Exception e) {
            fail("Failed to set test CSV file path: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Delete test CSV file after tests
        Files.deleteIfExists(Paths.get(TEST_CSV_FILE));
    }

    @Test
    public void testCreateNewUser_AppendsUser() throws IOException {
        userCreation.createNewUser("Scientist", "Alice Smith", "asmith", "pass789");

        List<String> lines = Files.readAllLines(Paths.get(TEST_CSV_FILE));

        // The new user should be appended, so lines count should be 4 (header + 3 users)
        assertEquals(4, lines.size());

        // Check last line for the new user data
        String lastLine = lines.get(lines.size() - 1);
        assertTrue(lastLine.contains("Alice Smith"));
        assertTrue(lastLine.contains("asmith"));
        assertTrue(lastLine.startsWith("Scientist,"));
    }

    @Test
    public void testCreateNewUser_AssignsValidID() throws IOException {
        userCreation.createNewUser("Administrator", "New Admin", "newadmin", "adminpass");

        List<String> lines = Files.readAllLines(Paths.get(TEST_CSV_FILE));
        String lastLine = lines.get(lines.size() - 1);
        String[] parts = lastLine.split(",");

        // ID should be an integer between 1000 and 9999 (per your code)
        int id = Integer.parseInt(parts[1]);
        assertTrue(id >= 1000 && id <= 9999);
    }
}
