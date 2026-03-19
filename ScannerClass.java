import java.util.Scanner;

/**
 * Provides a single shared {@code Scanner} instance for reading input from {@code System.in}.
 * 
 * <p>This class allows consistent and centralized input handling across the application
 * without creating multiple Scanner instances, which can cause resource conflicts.
 */
public class ScannerClass{
    /** Shared Scanner instance for standard input. */
    public static Scanner scan = new Scanner(System.in);
}

