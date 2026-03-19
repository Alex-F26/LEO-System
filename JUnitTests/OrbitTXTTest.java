import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class OrbitTXTTest {

    private static final String TEST_INPUT_FILE = "test-orbit.csv";
    private static final String TEST_OUTPUT_FILE = "test-output.txt";
    private OrbitTXT orbitTXT;

    @BeforeEach
    public void setUp() throws IOException {
        // Create a test CSV input file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_INPUT_FILE))) {
            writer.write("ID,Name,Status");
            writer.newLine();
            writer.write("1,ObjectA,Yes");
            writer.newLine();
            writer.write("2,ObjectB,No");
            writer.newLine();
            writer.write("3,ObjectC,Maybe");
            writer.newLine();
            writer.write("4,ObjectD,No");
            writer.newLine();
        }
        orbitTXT = new OrbitTXT();
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_INPUT_FILE));
        Files.deleteIfExists(Paths.get(TEST_OUTPUT_FILE));
    }

    @Test
    public void testGenerateTXT_CreatesFileAndCounts() throws IOException {
        orbitTXT.generateTXT(TEST_INPUT_FILE, TEST_OUTPUT_FILE);

        // Check counts
        assertEquals(4, orbitTXT.rowCount, "Total rows read (including header)");
        assertEquals(1, orbitTXT.yesCount);
        assertEquals(1, orbitTXT.maybeCount);
        assertEquals(2, orbitTXT.noCount);

        // Check output file content contains expected lines
        List<String> lines = Files.readAllLines(Paths.get(TEST_OUTPUT_FILE));

        // Should contain header and summary lines
        assertTrue(lines.get(0).contains("List of Exited Objects:"));
        assertTrue(lines.contains("|2|ObjectB|No "));
        assertTrue(lines.contains("|4|ObjectD|No "));
        assertTrue(lines.stream().anyMatch(l -> l.contains("Yes: 1")));
        assertTrue(lines.stream().anyMatch(l -> l.contains("Maybe: 1")));
        assertTrue(lines.stream().anyMatch(l -> l.contains("No: 2")));
    }
}
