import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SystemLoggerTest {

    private static final String LOG_FILE = "system_log.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @BeforeEach
    void cleanLogFileBefore() {
        File file = new File(LOG_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @AfterEach
    void cleanLogFileAfter() {
        File file = new File(LOG_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testLog_createsLogFileWithProperEntry() throws IOException {
        String testMessage = "Test log entry";

        SystemLogger.getInstance(testMessage);

        File file = new File(LOG_FILE);
        assertTrue(file.exists(), "Log file should exist after logging.");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            assertNotNull(line, "Log file should contain a log entry.");

            // Check format: [timestamp] message
            assertTrue(line.startsWith("["), "Log entry should start with '['");
            int closingBracketIndex = line.indexOf(']');
            assertTrue(closingBracketIndex > 0, "Log entry should contain closing ']'");

            String timestampStr = line.substring(1, closingBracketIndex);
            String messageLogged = line.substring(closingBracketIndex + 2); // skip "] "

            assertEquals(testMessage, messageLogged, "Logged message should match input message.");

            // Validate timestamp format parses correctly
            LocalDateTime.parse(timestampStr, FORMATTER);
        }
    }
}
