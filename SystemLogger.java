
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The {@code SystemLog} class provides a utility for logging messages
 * to a persistent log file with timestamped entries.
 * <p>
 * All entries are appended to the same file and include the current
 * timestamp at the time of logging. This class is useful for tracking
 * system interactions and user activity.
 * </p>
 * 
 * <p>Example log format:</p>
 * <pre>
 * [2025-07-06 16:10:33.245] Scientist logged in
 * </pre>
 * 
 * @author Alejandro Flores
 * @version 2.0
 */
public class SystemLogger {

    /** The name of the file where log messages are stored.*/
    private static final String LOG_FILE = "system_log.txt"; 

    /** Formatter to apply to the timestamp for log entries (includes milliseconds). */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private static SystemLogger single_instance = null;
    
    private SystemLogger(String message){
            log(message);
    }

    /**
     * Logs a message to the system log file with a timestamp.
     * <p>
     * Each log entry is prepended with the current timestamp in the format
     * {@code yyyy-MM-dd HH:mm:ss.SSS} and appended to {@code system_log.txt}.
     * </p>
     *
     * @param message the message to be logged
     */
    private static void log(String message){

            String timestamp = LocalDateTime.now().format(FORMATTER);
            String logEntry = "[" + timestamp + "] " + message;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
                writer.write(logEntry);
                writer.newLine();
        } catch (IOException e) {
                System.err.println("Logging Error: " + e.getMessage());
        }
    }

    public static SystemLogger getInstance(String message) {
        if (single_instance == null) {
                single_instance = new SystemLogger(message);
        }
        else{
            log(message);
        }
        return single_instance;
    }
}
